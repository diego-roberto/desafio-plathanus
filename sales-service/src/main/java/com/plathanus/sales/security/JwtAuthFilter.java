package com.plathanus.sales.security;

import com.plathanus.sales.dto.TokenPayloadDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${auth.service.url}")
    private String AUTH_SERVICE_URL;

    private final String AUTH_SERVICE_TOKEN_URL = "/auth/validate";

    private final RestTemplate restTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token ausente");
            return;
        }

        String token = authHeader.replace("Bearer ", "");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            String url = UriComponentsBuilder
                    .fromHttpUrl(AUTH_SERVICE_URL)
                    .path(AUTH_SERVICE_TOKEN_URL)
                    .toUriString();


            ResponseEntity<TokenPayloadDTO> responseEntity =
                    restTemplate.exchange(url,
                            org.springframework.http.HttpMethod.GET,
                            entity,
                            TokenPayloadDTO.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                TokenPayloadDTO payload = responseEntity.getBody();
                assert payload != null;

                List<SimpleGrantedAuthority> authorities = payload.roles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(payload.username(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido");
                return;
            }

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Erro ao validar token");
            return;
        }

        chain.doFilter(request, response);
    }

}
