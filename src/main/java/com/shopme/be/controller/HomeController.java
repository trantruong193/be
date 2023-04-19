package com.shopme.be.controller;

import com.shopme.be.persistant.dto.ResponseObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class HomeController {

    @Value("${host.name}")
    private String hostName;
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @GetMapping()
    public ResponseEntity<ResponseObject> home(){
        return ResponseEntity.status(200).body(ResponseObject
                .builder()
                        .status("Get info success")
                        .message("Welcome to my backend")
                        .data("Api docs: " + hostName + "/api-docs/")
                .build());
    }
}
