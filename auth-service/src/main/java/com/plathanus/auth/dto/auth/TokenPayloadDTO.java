package com.plathanus.auth.dto.auth;

import java.util.List;
import java.util.UUID;

public record TokenPayloadDTO(
        UUID uuid,
        String username,
        List<String> roles
) {}
