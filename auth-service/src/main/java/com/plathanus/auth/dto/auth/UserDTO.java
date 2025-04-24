package com.plathanus.auth.dto.auth;

import com.plathanus.auth.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class UserDTO {

    public UUID id;
    public String username;
    public Role role;

}
