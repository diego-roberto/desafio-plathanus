package com.plathanus.auth.controller;

import com.plathanus.auth.dto.auth.LoginDTO;
import com.plathanus.auth.dto.auth.TokenDTO;
import com.plathanus.auth.security.JwtUtil;
import com.plathanus.auth.service.AuthService;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> validate(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        try {
            boolean valid = jwtUtil.isTokenValid(token);
            return ResponseEntity.ok(valid ? "valid" : "invalid");
        } catch (JwtException e) {
            return ResponseEntity.ok("invalid");
        }
    }


}
