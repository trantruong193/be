package com.shopme.be.persistant.mapper;

import com.shopme.be.persistant.dto.UserDto;
import com.shopme.be.persistant.model.User;

public class UserMapper {
    public static UserDto toDto (User user) {
        return UserDto.builder()
            .id(user.getId())
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .createAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .address(user.getAddress())
            .birthday(user.getBirthday())
            .gender(user.getGender())
            .avatar(user.getAvatar())
            .tick(user.isTick())
            .role(user.getRole())
            .build();
    }
}
