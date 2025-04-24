package com.plathanus.auth.controller;

import com.plathanus.auth.dto.auth.UserDTO;
import com.plathanus.auth.entity.User;
import com.plathanus.auth.mapper.UserMapper;
import com.plathanus.auth.repository.UserRepository;
import com.plathanus.auth.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> listAll(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Claims claims = jwtUtil.extractAllClaims(token);
            List<String> roles = claims.get("roles", List.class);

            if (roles != null && roles.contains("ROLE_VENDOR")) {
                List<User> users = userRepository.findAll();
                List<UserDTO> dtos = users.stream().map(userMapper::toUserDTO).toList();
                return ResponseEntity.ok(dtos);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
