package com.shopme.be.service.impl;

import com.shopme.be.exception.ErrorCode;
import com.shopme.be.exception.MyException;
import com.shopme.be.persistant.dto.UserDto;
import com.shopme.be.persistant.mapper.UserMapper;
import com.shopme.be.persistant.model.User;
import com.shopme.be.repository.UserRepository;
import com.shopme.be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty())
            throw new MyException(ErrorCode.INVALID_INFORMATION);
        return UserMapper.toDto(user.get());
    }
    @Override
    public UserDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new MyException(ErrorCode.INVALID_INFORMATION);
        return UserMapper.toDto(user.get());
    }
    @Override
    public UserDto save(User user) {
        Optional<User> checkEmail = userRepository.findByEmail(user.getEmail());
        if (checkEmail.isPresent()){
            throw new MyException(ErrorCode.DUPLICATE_EMAIL);
        }
        try {
            User saved = userRepository.save(user);
            return UserMapper.toDto(saved);
        }catch (Exception e){
            throw new MyException(ErrorCode.SERVER_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = MyException.class)
    public UserDto update(UserDto userDto) {
        try {
            // check user is existed
            Optional<User> founded = userRepository.findById(userDto.getId());
            if (founded.isEmpty()){
                throw new MyException(ErrorCode.INVALID_INFORMATION);
            }
            // update new info
            User user = founded.get();
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            if (userDto.getNickname() != null && !userDto.getNickname().equals("")){
                user.setNickname(userDto.getNickname());
            }
            user.setAddress(userDto.getAddress());
            if (userDto.getBirthday() != null){
                user.setBirthday(userDto.getBirthday());
            }
            if (userDto.getGender() != null && !userDto.getGender().equals("")){
                user.setGender(userDto.getGender());
            }
            if (userDto.getAvatar() != null && !userDto.getAvatar().equals("")){
                user.setAvatar(userDto.getAvatar());
            }
            if (userDto.isTick())
                user.setTick(true);
            // return
            return UserMapper.toDto(user);
        }catch (Exception e){
            throw new MyException(ErrorCode.SERVER_ERROR);
        }
    }
}
