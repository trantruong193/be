package com.shopme.be.controller;

import com.shopme.be.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileStorageService fileStorageService;
    @Data @AllArgsConstructor @NoArgsConstructor
    public static class FileUploadResult{
        public String code;
        public String filename;
    }
    @Value("${host.name}")
    private String hostName;
    @Operation(summary = "Upload an image or multiple images")
    @PostMapping("")
    public ResponseEntity<?> upload (@RequestParam("file") MultipartFile[] file){
        StringBuffer servername = new StringBuffer(hostName + "/api/v1/files/");
        List<FileUploadResult> results = new ArrayList<>();
        for (MultipartFile f: file){
            fileStorageService.storageFile(f)
                    .thenApply(filename -> results.add(new FileUploadResult("Success",servername + filename)))
                    .exceptionally(ex -> results.add(new FileUploadResult("Fail",ex.getMessage())));
        }
        return ResponseEntity.status(201).body(results);
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
