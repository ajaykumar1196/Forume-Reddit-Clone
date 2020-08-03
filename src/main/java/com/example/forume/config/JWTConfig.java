package com.example.forume.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JWTConfig {

    @Value("${application.jwt.secretKey}")
    private String secretKey;

    @Value("${application.jwt.tokenExpirationAfterDays}")
    private Integer tokenExpirationAfterDays;

    public JWTConfig() {
    }

    private String getSecretKey() {
        return secretKey;
    }

    public Integer getTokenExpirationAfterDays() {
        return tokenExpirationAfterDays * 24 * 60 * 60 * 1000;
    }

    public String getAuthorizationHeader() {
        return "AUTHORIZATION";
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(getSecretKey().getBytes());
    }
}
