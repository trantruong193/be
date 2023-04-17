package com.shopme.be.service;

import com.shopme.be.persistant.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    void save(User user);
}
