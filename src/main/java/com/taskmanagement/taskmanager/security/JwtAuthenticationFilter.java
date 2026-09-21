package com.taskmanagement.taskmanager.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        
        if (token == null || isAlreadyAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            authenticateRequest(token, request);
        } catch (JwtException
                 | AuthenticationException
                 | IllegalArgumentException exception) {
            SecurityContextHolder.clearContext();
        }
        
        filterChain.doFilter(request, response);
    }
    
    private void authenticateRequest(
            String token,
            HttpServletRequest request) {
        String username = jwtService.extractUsername(token);
        
        if (!StringUtils.hasText(username)) {
            return;
        }
        
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);
        
        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
        
        authentication.setDetails(
                new WebAuthenticationDetailsSource()
                        .buildDetails(request)
        );
        
        SecurityContext context =
                SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
    
    private String getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader =
                request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (StringUtils.hasText(authorizationHeader)
                && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(
                    BEARER_PREFIX.length()
            );
        }
        
        return null;
    }
    
    private boolean isAlreadyAuthenticated() {
        return SecurityContextHolder.getContext()
                .getAuthentication() != null;
    }
}
