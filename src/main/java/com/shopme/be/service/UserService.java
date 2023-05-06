package com.shopme.be.service;

import com.shopme.be.persistant.dto.UserDto;
import com.shopme.be.persistant.model.User;

import java.util.Optional;

public interface UserService{
    UserDto findByEmail(String email);
    UserDto findById(Long id);
    UserDto save(User user);
    UserDto update(UserDto userDto);
}
