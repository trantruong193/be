package com.shopme.be.auth;

import com.shopme.be.config.JwtService;
import com.shopme.be.persistant.dto.UserDto;
import com.shopme.be.persistant.model.User;
import com.shopme.be.service.UserService;
import com.shopme.be.user.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Authentication register(RegisterRequest request) {

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .role(Role.ROLE_USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userService.save(user);
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles",user.getRole());
        var jwtToken = jwtService.generateToken(claims,request.getEmail());

        log.info("New user register {}",user);
        return Authentication.builder()
                .token(jwtToken).build();
    }

    public Authentication authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        UserDto userDto = userService.findByEmail(request.getEmail());
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles",userDto.getRole());
        var jwtToken = jwtService.generateToken(claims,request.getEmail());

        log.info("User just login: {}",request.getEmail());
        return Authentication.builder()
                .token(jwtToken).build();
    }
}
