package pp.ua.lifebook.web.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pp.ua.lifebook.web.user.LbUserPrincipal;

public class SecurityUtil {

    private SecurityUtil() {
        // hide public constructor
    }

    public static LbUserPrincipal getUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LbUserPrincipal) authentication.getPrincipal();
    }
}
