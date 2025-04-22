package ee.bitweb.transactions.config.security;


import org.springframework.security.core.context.SecurityContextHolder;

public class DetectorSecurityHelper {

    public static Long getId() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }

        DetectorToken token = (DetectorToken) SecurityContextHolder.getContext().getAuthentication();

        return token.getId();
    }

    public static String getName() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }

        DetectorToken token = (DetectorToken) SecurityContextHolder.getContext().getAuthentication();

        return token.getName();
    }
}
