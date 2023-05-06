package com.shopme.be.controller;

import com.shopme.be.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("api/v1/files")
@CrossOrigin
@RequiredArgsConstructor
public class FileUploadController {
    private final FileStorageService fileStorageService;

    @Value("${host.name}")
    private String hostName;
    @Operation(summary = "Upload an image")
    @PostMapping("")
    public ResponseEntity<?> upload (@RequestParam("file") MultipartFile file){
        String servername = hostName + "/api/v1/files/";
        try {
            String filename = fileStorageService.storageFile(file);
            return ResponseEntity.status(201).body(servername + filename);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(e.getMessage());
        }
    }
    @Operation(summary = "Get an image")
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<?> readDetailFile(@PathVariable String fileName){
        try {
            byte[] file = fileStorageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(e.getMessage());
        }
    }
}
