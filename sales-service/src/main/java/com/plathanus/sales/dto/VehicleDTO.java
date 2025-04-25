package com.plathanus.sales.dto;

import com.plathanus.sales.enums.Color;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record VehicleDTO(
        UUID id,
        int year,
        BigDecimal basePrice,
        Color color,
        String model,
        boolean available
) {}

