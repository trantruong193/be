package com.shopme.be.controller;

import com.shopme.be.auth.RegisterRequest;
import com.shopme.be.persistant.dto.ResponseObject;
import com.shopme.be.persistant.model.User;
import com.shopme.be.service.UserService;
import com.shopme.be.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping()
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok().body("Hello admin");
    }

    @PostMapping("create-employee")
    public ResponseEntity<ResponseObject> createEmployee(@RequestBody RegisterRequest request){
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_EMPLOYEE);
        userService.save(user);
        return ResponseEntity.status(201).body(new ResponseObject("Success","Create new employee",request));
    }
}
