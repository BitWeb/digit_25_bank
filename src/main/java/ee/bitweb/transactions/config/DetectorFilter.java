package ee.bitweb.transactions.config;


import ee.bitweb.transactions.config.security.DetectorToken;
import ee.bitweb.transactions.domain.detector.common.Detector;
import ee.bitweb.transactions.domain.detector.feature.DetectorFinder;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class DetectorFilter implements Filter {

    private static final int LIMIT = 50;
    private final ConcurrentHashMap<String, Context> contexts = new ConcurrentHashMap<>();
    private final DetectorFinder finder;
    private final MeterRegistry meterRegistry;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException  {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        Context context = null;
        String tokenHeader = req.getHeader("Authorization");
        if (tokenHeader != null) {
            context = getOrCreate(tokenHeader);

            if (context != null) {
                SecurityContextHolder.getContext().setAuthentication(
                        new DetectorToken(
                                context.getDetector().getId(),
                                context.getDetector().getName(),
                                List.of(new SimpleGrantedAuthority("ROLE_USER"))
                        )
                );
            }
        }
        try {

            if (context != null) {
                if (context.isAtLimit()) {
                    HttpServletResponse res = (HttpServletResponse) servletResponse;
                    res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    return;
                }
                context.bump(1);
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if (context != null) {
                context.bump(-1);
            }
        }


    }

    private Context getOrCreate(String token) {
        if (contexts.containsKey(token)) {

            return contexts.get(token);
        }

        Detector detector = finder.findByToken(token);
        if (detector == null) {return null;}

        AtomicInteger count = new AtomicInteger(0);
        Context context =  new Context(
                count,
                Gauge.builder(
                        "detector_concurrent_requests",
                        count,
                        AtomicInteger::get
                ).strongReference(true).tags("detector", detector.getName()).register(meterRegistry),
                detector
        );
        contexts.put(token, context);

        return context;
    }

    @Getter
    @RequiredArgsConstructor
    private static class Context {

        private final AtomicInteger count;
        private final Gauge gauge;
        private final Detector detector;

        synchronized void bump(int value) {
            count.addAndGet(value);

        }

        boolean isAtLimit() {
            return count.get() > LIMIT;
        }
    }
}
