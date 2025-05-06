package com.example.springauth.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    protected Log logger = LogFactory.getLog(this.getClass());
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException{
        this.handle(request, response, authentication);
        this.clearAuthenticationAttributes(request);
    }

    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        String targetUrl = this.determineTargetUrl(authentication);
        if (response.isCommitted()){
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }


    protected String determineTargetUrl(Authentication authentication) {
        Map<String, String> roleTargetURLMap = new HashMap<>();
        roleTargetURLMap.put("ROLE_ADMIN", "/control_panel");
        roleTargetURLMap.put("ROLE_USER", "/home");
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority authority : authorities) {
            String authorityName = authority.getAuthority();
            if (roleTargetURLMap.containsKey(authorityName)) {
                return roleTargetURLMap.get(authorityName);
            }
        }
        throw new IllegalStateException("Couldn't determine the user's authority or role");
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) throws NullPointerException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }


}
