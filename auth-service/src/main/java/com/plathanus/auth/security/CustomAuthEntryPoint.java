package com.plathanus.auth.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        Object jwtEx = request.getAttribute("JWT_EXCEPTION");

        if (jwtEx instanceof ExpiredJwtException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expirado");
        } else if (jwtEx instanceof JwtException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Autenticação obrigatória");
        }

        System.out.println("[AUTH FAIL] - " + authException.getMessage());
    }

}
