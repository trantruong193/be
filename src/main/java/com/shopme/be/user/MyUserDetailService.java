package com.shopme.be.user;

import com.shopme.be.persistant.model.User;
import com.shopme.be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public MyUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email).orElse(null);
        if (user != null){
            return new MyUserDetail(user);
        }else {
            throw new UsernameNotFoundException("Username hasn't existed");
        }
    }
}
