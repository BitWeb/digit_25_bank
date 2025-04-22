package ee.bitweb.transactions.config;


import ee.bitweb.transactions.config.security.DetectorToken;
import ee.bitweb.transactions.domain.detector.common.Detector;
import ee.bitweb.transactions.domain.detector.feature.DetectorFinder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DetectorFilter implements Filter {

    private final DetectorFinder finder;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException  {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        String tokenHeader = req.getHeader("Authorization");
        if (tokenHeader != null) {
            Detector detector = finder.findByToken(tokenHeader);
            if (detector != null) {
                SecurityContextHolder.getContext().setAuthentication(
                        new DetectorToken(
                                detector.getId(),
                                detector.getName(),
                                List.of(new SimpleGrantedAuthority("ROLE_USER"))
                        )
                );
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
