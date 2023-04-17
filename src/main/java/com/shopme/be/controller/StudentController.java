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
    ResponseEntity<ResponseObject> getAll(@PageableDefault(size = 3) Pageable pageable){

        Page<StudentDto> students = studentService.getAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseObject("Ok","Get All students successfully",students));

    }
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getById(@PathVariable String id){

        StudentDto studentDto = studentService.getById(Long.valueOf(id));

        if (studentDto != null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseObject("Ok","Details of student with ID: " + id,studentDto));
        }else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("Not Found","No Detail of student with ID: " + id,""));
        }
    }
    @PostMapping()
    ResponseEntity<ResponseObject> save(@RequestBody StudentDto studentDto){

        StudentDto student = studentService.saveStudent(studentDto);
        if (student!=null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseObject("Insert Ok","Insert student successfully",student));
        }else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("Insert fail","Insert student fail",""));
        }
    }
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteBy(@PathVariable String id){

        boolean delete = studentService.deleteStudent(Long.valueOf(id));

        if (delete){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseObject("Delete Ok","Delete success student with ID: " + id,""));
        }else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("Delete fail","Can't delete student with ID: " + id,""));
        }
    }
    @PutMapping("")
    ResponseEntity<ResponseObject> update(@RequestBody StudentDto studentDto){

        StudentDto studentDto1 = studentService.updateStudent(studentDto);


        if (studentDto1 != null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseObject("Update Ok","Update success fully",studentDto1));
        }else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("Update fail","Can't update student",""));
        }
    }

}
