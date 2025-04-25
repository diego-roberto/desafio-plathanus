package com.plathanus.sales.dto;

import java.util.List;
import java.util.UUID;

public record TokenPayloadDTO(
        UUID id,
        String username,
        List<String> roles
) {}
