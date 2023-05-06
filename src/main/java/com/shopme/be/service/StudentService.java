package com.shopme.be.service;

import com.shopme.be.persistant.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService extends BaseService<StudentDto>{
    Page<StudentDto> getAll(Pageable pageable);
    StudentDto getByEmail(String email);

}
