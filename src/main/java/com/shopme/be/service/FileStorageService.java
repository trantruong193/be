package com.shopme.be.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;


public interface FileStorageService {

    CompletableFuture<String> storageFile (MultipartFile file);

    CompletableFuture<byte[]> readFileContent(String fileName);
}
