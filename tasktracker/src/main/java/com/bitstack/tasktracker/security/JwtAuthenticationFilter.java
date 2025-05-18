package com.bitstack.tasktracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
    			// Log the incoming request URL
        final String authHeader = request.getHeader("Authorization");
        
        // Log the incoming request URL
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7); // Remove "Bearer "
        System.out.println("🪪 Extracted JWT: " + jwt);
      
        // Check if JWT is empty or null
        if (jwt == null || jwt.trim().isEmpty()) {
            System.out.println("❌ Empty or null JWT received.");
            filterChain.doFilter(request, response);
            return;
        }
        
        // Check if JWT is malformed
        String username = null;
        try {
        
        	// Extract username from JWT
            username = jwtUtil.extractUsername(jwt);
        } catch (Exception e) {
            System.out.println("❌ JWT extraction failed: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }
        
        	// Check if the user is already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            	// Check if the JWT is valid
            if (jwtUtil.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set the authentication details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("❌ JWT validation failed");
            }
        }

        filterChain.doFilter(request, response);
    }
}
