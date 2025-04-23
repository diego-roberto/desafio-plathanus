package com.plathanus.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class EncryptionKeyConfig {

    @Value("${aes.secret}")
    private String secret;

    @Bean
    public SecretKey aesSecretKey() {
        return new SecretKeySpec(secret.getBytes(), "AES");
    }

}

