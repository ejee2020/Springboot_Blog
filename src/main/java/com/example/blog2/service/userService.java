package com.example.blog2.service;

import com.example.blog2.dto.userDTO;
import com.example.blog2.entity.user;

import java.util.Optional;

public interface userService {
    String signUp(userDTO UserDTO);
    Optional<user> getUser(String id);
    boolean matchPassword(String raw, String encoded);
    boolean duplicate_id(String id);
    user find_by_name_and_email(userDTO UserDTO);
    user find_by_name_email_and_id(userDTO UserDTO);
    Optional<user> find_by_id(String name);
    public String updateUser(user User);
    default user dtoToEntity(userDTO userDTO){
        user User = user.builder()
                .id(userDTO.getId())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .build();
        return User;
    }
    default userDTO entityToDTO(user User){
        userDTO UserDTO = userDTO.builder()
                .id(User.getId())
                .password(User.getPassword())
                .email(User.getEmail())
                .name(User.getName())
                .build();
        return UserDTO;
    }
}
