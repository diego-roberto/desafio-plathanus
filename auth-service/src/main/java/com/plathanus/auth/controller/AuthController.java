package com.plathanus.auth.controller;

import com.plathanus.auth.dto.auth.LoginDTO;
import com.plathanus.auth.dto.auth.TokenDTO;
import com.plathanus.auth.dto.auth.TokenPayloadDTO;
import com.plathanus.auth.security.JwtUtil;
import com.plathanus.auth.service.AuthService;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody @Valid LoginDTO dto) {
        return authService.login(dto);
    }

    @GetMapping("/validate")
    public ResponseEntity<TokenPayloadDTO> validate(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        try {
            if (!jwtUtil.isTokenValid(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String username = jwtUtil.extractUsername(token);
            List<String> roles = jwtUtil.extractClaim(token, claims -> claims.get("roles", List.class));

            return ResponseEntity.ok(new TokenPayloadDTO(username, roles));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
