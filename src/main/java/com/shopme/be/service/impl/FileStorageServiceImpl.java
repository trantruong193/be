package com.shopme.be.service.impl;

import com.shopme.be.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {
    private final Path storageFolder = Paths.get("uploads");

    public FileStorageServiceImpl(){
        // Creat storage folder if not exist
        try {
            Files.createDirectories(storageFolder);
        }catch (IOException ioException){
            throw new RuntimeException("Cannot initialize storage folder" + ioException.getMessage());
        }
    }
    private boolean isImageFile(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileExtension != null;
        return Arrays.asList(new String[] {"png","jpg","jpeg","bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }
    @Override
    @Async("uploadFileExecutor")
    public CompletableFuture<String> storageFile(MultipartFile file) {
        try {
            long start = System.currentTimeMillis();
            // Check file is empty
            if (file.isEmpty()){
                throw new RuntimeException("Failed to storage empty file");
            }
            // Check file size must less than 3Mb
            float fileSizeInMb = file.getSize()/1_000_000.0f;
            if (fileSizeInMb>3.0f){
                throw new RuntimeException("Storage file is over than 3Mb");
            }
            // check if not image file
            if(!isImageFile(file)){
                throw new RuntimeException("You can only upload image file");
            }
            // Creat new file
            String filenameExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFilename = UUID.randomUUID().toString().replace("-","");
            generatedFilename = generatedFilename + "." + filenameExtension;
            Path destinationFilename = this.storageFolder.resolve(Paths.get(generatedFilename)).normalize().toAbsolutePath();
            // Save file
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream,destinationFilename, StandardCopyOption.REPLACE_EXISTING);

            log.info("Executed in: {} ms",System.currentTimeMillis()-start);
            return CompletableFuture.completedFuture(generatedFilename);

        }catch (Exception e){
            throw new RuntimeException("Can't store file: " + e.getMessage());
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try{
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                return StreamUtils.copyToByteArray(resource.getInputStream());
            }else {
                throw new RuntimeException("File is not existed");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read file: " + fileName + ". " + e.getMessage());
        }
    }
}
