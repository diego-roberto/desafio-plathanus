package com.plathanus.sales.service;

import com.plathanus.sales.config.AuthServiceProperties;
import com.plathanus.sales.dto.UserDTO;
import com.plathanus.sales.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private final AuthServiceProperties properties;

    public List<UserDTO> findAll() {
        String url = properties.getUrl() + "/users";

        HttpHeaders headers = new HttpHeaders();
        String token = TokenUtil.getCurrentRequestToken();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UserDTO[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UserDTO[].class
        );
        return List.of(response.getBody());
    }

}
