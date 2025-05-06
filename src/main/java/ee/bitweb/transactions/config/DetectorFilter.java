package ee.bitweb.transactions.config;


import ee.bitweb.transactions.config.security.DetectorToken;
import ee.bitweb.transactions.domain.detector.common.Detector;
import ee.bitweb.transactions.domain.detector.feature.DetectorFinder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class DetectorFilter implements Filter {

    private static final int LIMIT = 50;
    private final DetectorFinder finder;
    private final ConcurrentHashMap<String, Integer> counters = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Detector> detectors = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException  {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        String ratelimitedToken = null;
        String tokenHeader = req.getHeader("Authorization");
        if (tokenHeader != null) {
            Detector detector = getDetector(tokenHeader);
            if (detector != null) {
                SecurityContextHolder.getContext().setAuthentication(
                        new DetectorToken(
                                detector.getId(),
                                detector.getName(),
                                List.of(new SimpleGrantedAuthority("ROLE_USER"))
                        )
                );
                ratelimitedToken = tokenHeader;
            }
        }
        if (ratelimitedToken != null) {
            if (counters.containsKey(ratelimitedToken) && counters.get(ratelimitedToken) > LIMIT) {
                HttpServletResponse res = (HttpServletResponse) servletResponse;
                res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                return;
            }
            bumpCounter(ratelimitedToken);
        }

        filterChain.doFilter(servletRequest, servletResponse);

        debumpCounter(ratelimitedToken);
    }

    private Detector getDetector(String tokenHeader) {
        if (detectors.containsKey(tokenHeader)) {
            return detectors.get(tokenHeader);
        }
        Detector detector = finder.findByToken(tokenHeader);
        detectors.put(tokenHeader, detector);

        return detector;
    }

    private void bumpCounter(String ratelimitedToken) {
        if (counters.containsKey(ratelimitedToken)) {
            counters.put(ratelimitedToken, counters.get(ratelimitedToken) + 1);
        } else {
            counters.put(ratelimitedToken, 1);
        }
    }

    private void debumpCounter(String ratelimitedToken) {
        if (ratelimitedToken != null) {
            counters.put(ratelimitedToken, counters.get(ratelimitedToken) - 1);
        }
    }
}
