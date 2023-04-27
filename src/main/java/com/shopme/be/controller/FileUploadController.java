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

import java.util.concurrent.CompletableFuture;

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
    public ResponseEntity<String> upload (@RequestParam("file") MultipartFile file){
        try {
            String servername = hostName + "/api/v1/files/";
            CompletableFuture<String> filename = fileStorageService.storageFile(file);
            return ResponseEntity.status(201).body(servername + filename.get());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("");
        }
    }
    @Operation(summary = "Get an image")
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<?> readDetailFile(@PathVariable String fileName){
        try {
            CompletableFuture<byte[]> file = fileStorageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file.get());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(e.getMessage());
        }
    }
}
