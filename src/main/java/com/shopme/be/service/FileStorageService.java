package com.shopme.be.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileStorageService {

    public String storageFile (MultipartFile file);

    public byte[] readFileContent(String fileName);
}
