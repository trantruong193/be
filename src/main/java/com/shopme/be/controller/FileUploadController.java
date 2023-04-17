package com.shopme.be.controller;

import com.shopme.be.persistant.dto.ResponseObject;
import com.shopme.be.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("api/v1/files")
@CrossOrigin
public class FileUploadController {
    @Autowired
    public FileStorageService fileStorageService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> upload (@RequestParam("file") MultipartFile file){
        try {
            String generateFilename = fileStorageService.storageFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("" +
                    "ok","store success",generateFilename));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("" +
                    "false","store fail",""));
        }
    }
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName){
        try {
            byte[] bytes = fileStorageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }catch (Exception exception){
            return ResponseEntity.noContent().build();
        }
    }

}
