package com.shopme.be.controller;

import com.shopme.be.persistant.dto.ResponseObject;
import com.shopme.be.service.FileStorageService;
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
    private final String hostname;

    @PostMapping("")
    public ResponseEntity<ResponseObject> upload (@RequestParam("file") MultipartFile file){

        try {
            StringBuffer servername = new StringBuffer(hostname);
            String generateFilename = fileStorageService.storageFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok","store success",servername + generateFilename));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("False","Store fail",e.getMessage()));
        }
    }
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<?> readDetailFile(@PathVariable String fileName){
        try {
            byte[] bytes = fileStorageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(exception.getMessage());
        }
    }

}
