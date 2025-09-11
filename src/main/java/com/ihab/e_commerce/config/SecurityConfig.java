package com.ihab.e_commerce.config;


import com.ihab.e_commerce.data.enums.Role;
import com.ihab.e_commerce.filter.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ihab.e_commerce.data.enums.Role.ADMIN;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/api/v1/auth/**")
                                .permitAll()
                                .requestMatchers(GET, "/api/v1/products/**")
                                .permitAll()
                                .requestMatchers(GET, "/api/v1/categories/**")
                                .permitAll()
//                                .requestMatchers(GET,"/api/v1/category/**")
//                                .permitAll()
//                                .requestMatchers(POST,"/api/v1/category/**").hasRole(ADMIN.name())
//                                .requestMatchers(PUT,"/api/v1/category/**").hasRole(ADMIN.name())
//                                .requestMatchers(DELETE,"/api/v1/category/**").hasRole(ADMIN.name())
                                .anyRequest()
                                .authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
