package com.shopme.be.controller;

import com.shopme.be.persistant.dto.ApiTutorial;
import com.shopme.be.persistant.dto.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HomeController {
    @GetMapping("")
    public ResponseEntity<ResponseObject> home(){
        return ResponseEntity.status(200).body(ResponseObject
                .builder()
                        .status("Get info success")
                        .message("Welcome to my backend")
                        .data(new ApiTutorial())
                .build());
    }
}
