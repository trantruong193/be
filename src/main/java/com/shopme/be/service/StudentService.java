package com.shopme.be.service;

import com.shopme.be.persistant.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

public interface StudentService extends BaseService<StudentDto>{
    CompletableFuture<Page<StudentDto>> getAll(Pageable pageable) throws InterruptedException;
    StudentDto getByEmail(String email);

}
