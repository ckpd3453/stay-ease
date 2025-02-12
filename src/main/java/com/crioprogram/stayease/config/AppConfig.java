package com.crioprogram.stayease.config;

import com.crioprogram.stayease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AppConfig {

    private final JWTAuthFilter jwtAuthFilter;
    private final UserRepository userRepository;

    @Autowired
    public AppConfig(JWTAuthFilter jwtAuthFilter, UserRepository userRepository) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/users/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/hotels/get-hotels").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/hotels/get-hotel/{hotelId}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v1/hotels/{hotelId}").hasRole("HOTEL_MANAGER")
                .requestMatchers(HttpMethod.POST, "/api/v1/hotels/add-hotel").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/hotels/{hotelId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/hotel-booking").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/cancel-booking/{booingId}").hasRole("HOTEL_MANAGER")

                .requestMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
