package com.plathanus.sales.dto;

import com.plathanus.sales.enums.Role;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String username,
        Role role
) {}

