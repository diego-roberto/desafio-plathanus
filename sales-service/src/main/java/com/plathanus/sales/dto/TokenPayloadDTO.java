package com.plathanus.sales.dto;

import java.util.List;

public record TokenPayloadDTO(
        String username,
        List<String> roles
) {}
