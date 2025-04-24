package com.plathanus.auth.util;

import com.plathanus.auth.security.JwtUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de geração e validade de Token")
class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secureKey = Encoders.BASE64.encode(key.getEncoded());
        ReflectionTestUtils.setField(jwtUtil, "secret", secureKey);
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", 60_000L);
        System.out.println("[SETUP] jwtUtil initialized with secret and expiration.");
    }

    @Test
    @DisplayName("testa a geração e validade de Token")
    void shouldGenerateAndValidateTokenSuccessfully() {
        UserDetails user = User.withUsername("admin").password("pass").roles("VENDOR").build();

        String token = jwtUtil.generateToken(user);
        System.out.println("[TEST] Generated token: " + token);
        boolean result = jwtUtil.isTokenValid(token, user);
        System.out.println("[TEST] Token valid: " + result);
        assertTrue(result);
    }

    @Test
    @DisplayName("testa rejeicao com Token invalido")
    void shouldFailOnInvalidToken() {
        UserDetails user = User.withUsername("admin").password("pass").roles("VENDOR").build();

        String fakeToken = "invalid.token.bluhbluhbluh";
        System.out.println("[TEST] Using invalid token: " + fakeToken);
        assertFalse(jwtUtil.isTokenValid(fakeToken, user));
    }

    @Test
    @DisplayName("testa rejeicao de Token expirado")
    void shouldRejectExpiredToken() throws InterruptedException {
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", 10L); // 10ms

        UserDetails user = User.withUsername("admin").password("pass").roles("VENDOR").build();
        String token = jwtUtil.generateToken(user);
        System.out.println("[TEST] Generated token (will expire quickly): " + token);

        Thread.sleep(50); // 50ms

        assertFalse(jwtUtil.isTokenValid(token, user));
    }

}