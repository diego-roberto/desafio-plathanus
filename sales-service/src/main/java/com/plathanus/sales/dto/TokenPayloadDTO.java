package com.plathanus.sales.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record TokenPayloadDTO(
        @JsonProperty("uuid") UUID id,
        String username,
        List<String> roles
) {}
