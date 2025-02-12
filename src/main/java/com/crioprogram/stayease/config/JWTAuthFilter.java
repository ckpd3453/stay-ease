package com.crioprogram.stayease.config;

import com.crioprogram.stayease.model.User;
import com.crioprogram.stayease.repository.UserRepository;
import com.crioprogram.stayease.utility.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserRepository userRepository;

    public JWTAuthFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (jwtService.validateToken(token)) {
            String email = jwtService.getEmailFromToken(token);

            // Load user from DB instead of using UserDetailsService
            User user = userRepository.findByEmailId(email);
            if (user == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmailId())
                    .password(user.getPassword())
                    .roles(user.getRole().replace("ROLE_", ""))  // Remove "ROLE_" prefix if present
                    .build();

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
            );
        }
        filterChain.doFilter(request, response);

    }
}
