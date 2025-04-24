package com.plathanus.auth.dto.auth;

import java.util.List;

public record TokenPayloadDTO(
        String username,
        List<String> roles
) {}
