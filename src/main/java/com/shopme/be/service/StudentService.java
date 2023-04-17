package com.shopme.be.service;

import com.shopme.be.persistant.dto.StudentDto;
import com.shopme.be.persistant.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    Page<StudentDto> getAll(Pageable pageable);
    StudentDto getById(Long id);
    StudentDto getByEmail(String email);
    boolean deleteStudent(Long id);
    StudentDto saveStudent(StudentDto student);
    StudentDto updateStudent(StudentDto student);

}
