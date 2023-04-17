package com.shopme.be.service.impl;

import com.shopme.be.persistant.model.User;
import com.shopme.be.repository.UserRepository;
import com.shopme.be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        try {
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
