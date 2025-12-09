package com.ihab.e_commerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebConfig {
    @Value("${tap.secret_key}")
    private String secretKey;
@Bean
public WebClient tapWepClient() {
    return WebClient.builder()
            .baseUrl("https://api.tap.company/v2")
            .defaultHeader(HttpHeaders.AUTHORIZATION,"Bearer " + secretKey)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
}

    @Bean
    public CorsConfigurationSource corsConfigurationSource () {
        CorsConfiguration config = new CorsConfiguration();
        // this to allow all header that common from that req
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("http://localhost:4200");
        // to allow cookies and session and authorities
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        /* (/**) this mean that all above apply on all controller*/
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
