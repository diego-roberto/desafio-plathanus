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
        byte[] keyBytes = hexToBytes(secret);
        return new SecretKeySpec(keyBytes, "AES");
    }

    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        if (len % 2 != 0) throw new IllegalArgumentException("Hex string must have even length");
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }

}

