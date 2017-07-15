package ru.yusupov.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.GenericFilterBean;
import ru.yusupov.security.auth.TokenAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenAuthFilter extends GenericFilterBean {
    private static final String AUTH_TOKEN = "Auth-Token";

    private AuthenticationManager authenticationManager;

    public TokenAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        System.out.println(((HttpServletRequest) servletRequest).getRequestURI());
        try {
            String token = extractTokenFromHeaders(httpServletRequest);
            if (token == null) {
                token = extractTokenFromCookies(httpServletRequest);
            }
            if (isNotRequiringProtection(httpServletRequest)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else if (token == null || token.equals("")) {
                throw new IllegalArgumentException("Token not found");
            } else {
                authenticationManager.authenticate(new TokenAuthentication(token));
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (AuthenticationException authenticationException) {
            throw new IllegalArgumentException(authenticationException);
        }
    }

    private boolean isNotRequiringProtection(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/users") && request.getMethod().equals("POST")
                || request.getRequestURI().startsWith("/login") && request.getMethod().equals("POST")
                || request.getRequestURI().equals("/signin.html") && request.getMethod().equals("GET")
                || request.getRequestURI().equals("/registration.html") && request.getMethod().equals("GET")
                || request.getRequestURI().startsWith("/authHandler") && request.getMethod().equals("GET")
                || request.getRequestURI().startsWith("/chat") && request.getMethod().equals("GET")
                || request.getRequestURI().equals("/chat_list.html") && request.getMethod().equals("GET")
                || request.getRequestURI().startsWith("/js") && request.getMethod().equals("GET")
                || request.getRequestURI().startsWith("/css") && request.getMethod().equals("GET")
                || request.getRequestURI().endsWith("favicon.ico");
    }

    private String extractTokenFromHeaders(HttpServletRequest request) {
        return request.getHeader(AUTH_TOKEN);
    }

    private String extractTokenFromCookies(HttpServletRequest request) {
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Auth-Token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}