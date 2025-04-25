package com.plathanus.sales.dto;

import com.plathanus.sales.enums.SaleType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SaleDTO(
        UUID clientId,
        UUID vehicleId,
        UUID sellerId,
        SaleType type
) {}

