package com.shopme.be.auth;

import com.shopme.be.config.JwtService;
import com.shopme.be.persistant.model.User;
import com.shopme.be.service.UserService;
import com.shopme.be.user.MyUserDetail;
import com.shopme.be.user.MyUserDetailService;
import com.shopme.be.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder,
                       UserService userService, JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Authentication register(RegisterRequest request) {

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .role(Role.USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userService.save(user);

        var jwtToken = jwtService.generateToken(request.getEmail());
        return Authentication.builder()
                .token(jwtToken).build();
    }

    public Authentication authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        var jwtToken = jwtService.generateToken(request.getEmail());
        return Authentication.builder()
                .token(jwtToken).build();
    }
}
