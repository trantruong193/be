package com.shopme.be.controller;

import com.shopme.be.persistant.dto.ResponseObject;
import com.shopme.be.persistant.dto.StudentDto;
import com.shopme.be.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping("api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping()
    ResponseEntity<ResponseObject> getAllByPage(@PageableDefault(size = 10) Pageable pageable) throws ExecutionException, InterruptedException {
        CompletableFuture<Page<StudentDto>> students = studentService.getAll(pageable);
        return ResponseEntity.status(200).body(new ResponseObject("Ok","Get students success",students.get()));
    }
    @GetMapping("{id}")
    ResponseEntity<ResponseObject> getById(@PathVariable Long id) throws InterruptedException, ExecutionException {
        CompletableFuture<StudentDto> studentDto = studentService.findById(id);
        return ResponseEntity.status(200).body(new ResponseObject("Ok","Student with Id: " + id,studentDto.get()));
    }
    @PostMapping()
    ResponseEntity<ResponseObject> save(@RequestBody StudentDto studentDto){

        StudentDto student = studentService.add(studentDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseObject("Insert Ok","Insert student successfully",student));
    }
    @DeleteMapping("{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        studentService.remove(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseObject("Delete Ok","Delete success student with ID: " + id,""));
    }
    @PutMapping("")
    ResponseEntity<ResponseObject> update(@RequestBody StudentDto studentDto){
        StudentDto saved = studentService.update(studentDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseObject("Update Ok","Update success fully",saved));
    }

}
