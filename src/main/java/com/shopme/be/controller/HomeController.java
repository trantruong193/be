package com.shopme.be.controller;

import com.shopme.be.persistant.dto.ResponseObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping()
public class HomeController {

    @Value("${host.name}")
    private String hostName;

    @GetMapping()
    public ResponseEntity<ResponseObject> home(){

        Map<String,String> tutorial = new HashMap<>();
        tutorial.put("Api docs (yaml format)",hostName + "/api-docs.yaml");
        tutorial.put("Api docs",hostName + "/api-docs/");

        return ResponseEntity.status(200).body(ResponseObject
                .builder()
                        .status("Get tutorial success")
                        .message("Welcome to my backend")
                        .data(tutorial)
                .build());
    }
}
