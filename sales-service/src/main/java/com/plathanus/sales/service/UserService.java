package com.plathanus.sales.service;

import com.plathanus.sales.config.AuthServiceProperties;
import com.plathanus.sales.dto.UserDTO;
import com.plathanus.sales.util.TokenUtil;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private final AuthServiceProperties properties;

    public Page<UserDTO> findAll(Pageable pageable) {
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

}
