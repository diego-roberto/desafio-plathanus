package com.plathanus.auth.entity;

import com.plathanus.auth.enums.Role;
import com.plathanus.auth.security.AESEncryptionConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private UUID id;

    @Convert(converter = AESEncryptionConverter.class)
    private String cpf;

    @Convert(converter = AESEncryptionConverter.class)
    private String name;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}

