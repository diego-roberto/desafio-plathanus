package com.plathanus.auth.mapper;

import com.plathanus.auth.dto.auth.UserDTO;
import com.plathanus.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public User toUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .role(userDTO.getRole())
                .build();
    }

}
