package com.shopme.be.controller;

import com.shopme.be.persistant.dto.ResponseObject;
import com.shopme.be.persistant.dto.UserDto;
import com.shopme.be.service.UserService;
import com.shopme.be.user.MyUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("")
    public ResponseEntity<ResponseObject> update(@RequestBody UserDto dto){

        UserDto userDto = userService.findById(dto.getId());
        // check if who send request is user
        if (checkIsUser(userDto))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ResponseObject("404", "You do not have permission to access this user info", ""));

        UserDto saved = userService.update(dto);
        return ResponseEntity.ok().body(
                new ResponseObject("200","Update new information success",saved)
        );
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        // find user
        UserDto userDto = userService.findById(id);
        // check if who send request is user
        if (checkIsUser(userDto)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ResponseObject("404", "You do not have permission to access this user info", "")
        );
        return ResponseEntity.ok().body(
                new ResponseObject("200","User information",userDto)
        );
    }
    private boolean checkIsUser(@RequestBody UserDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
            String email = myUserDetail.getUsername();
            return !Objects.equals(email, dto.getEmail());
        }
        return false;
    }
}
