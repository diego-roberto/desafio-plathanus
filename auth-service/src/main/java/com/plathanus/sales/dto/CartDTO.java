package com.plathanus.sales.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CartDTO(
        UUID id,
        UUID vehicle,
        LocalDateTime createdAt
) {}
