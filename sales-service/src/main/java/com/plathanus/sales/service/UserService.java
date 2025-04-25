package com.plathanus.sales.service;

import com.plathanus.sales.dto.TokenPayloadDTO;
import com.plathanus.sales.dto.UserDTO;
import com.plathanus.sales.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;

    @Value("${auth.service.url}")
    private String auth_url;

    public Page<UserDTO> findAll(Pageable pageable) {
        String url = auth_url + "/users";

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

        UserDTO[] userDTOArray = response.getBody();
        if (userDTOArray == null || userDTOArray.length == 0) {
            return Page.empty(pageable);
        }
        List<UserDTO> userDTOList = Arrays.asList(userDTOArray);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, userDTOList.size());

        List<UserDTO> currentPageList = userDTOList.subList(start, end);

        return new PageImpl<>(
                currentPageList,
                pageable,
                userDTOList.size()
        );

    }

    public Optional<UserDTO> findUserById(UUID id, String token) {
        String url = auth_url + "/users/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, UserDTO.class
        );

        return Optional.ofNullable(response.getBody());

    }

    public TokenPayloadDTO getTokenPayload(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<TokenPayloadDTO> response = restTemplate.exchange(
                auth_url + "/auth/validate",
                HttpMethod.GET,
                entity,
                TokenPayloadDTO.class
        );

        TokenPayloadDTO payload = response.getBody();
        if (payload == null || payload.id() == null) {
            throw new IllegalStateException("Falha ao validar token ou identificar usuário");
        }
        return payload;
    }

}
