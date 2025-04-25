package com.plathanus.sales.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class TokenUtil {

    public static String getCurrentRequestToken() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7);
            }
        }
        throw new IllegalStateException("Token ausente na requisição original");
    }
    public static UUID extractUserId(String token) {
        try {
            String[] parts = token.split("\\.");
            String payload = new String(Base64.decodeBase64(parts[1]), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(payload);
            return UUID.fromString(json.get("userId").asText());
        } catch (Exception e) {
            throw new IllegalArgumentException("Falha ao extrair userId do token", e);
        }
    }

}
