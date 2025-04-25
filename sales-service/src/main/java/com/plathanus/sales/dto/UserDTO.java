package com.plathanus.sales.dto;

import com.plathanus.sales.enums.Role;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDTO(
        UUID id,
        String username,
        Role role
) {}

