package com.shopme.be.controller;

import com.shopme.be.exception.ErrorCode;
import com.shopme.be.exception.MyException;
import com.shopme.be.persistant.dto.KlassDto;
import com.shopme.be.persistant.dto.ResponseObject;
import com.shopme.be.persistant.dto.StudentDto;
import com.shopme.be.persistant.model.Klass;
import com.shopme.be.repository.KlassRepository;
import com.shopme.be.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final KlassRepository klassRepository;

    @GetMapping()
    ResponseEntity<ResponseObject> getAllByPage(@PageableDefault(size = 10) Pageable pageable) {
        Page<StudentDto> students = studentService.getAll(pageable);
        return ResponseEntity.status(200).body(new ResponseObject("Ok","Get students success",students));
    }
    @GetMapping("{id}")
    ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        StudentDto studentDto = studentService.findById(id);
        return ResponseEntity.status(200).body(new ResponseObject("Ok","Student with Id: " + id,studentDto));
    }
    @PostMapping()
    ResponseEntity<ResponseObject> save(@Valid @RequestBody StudentDto studentDto){
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
    ResponseEntity<ResponseObject> update(@Valid @RequestBody StudentDto studentDto){
        StudentDto saved = studentService.update(studentDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseObject("Update Ok","Update success fully",saved));
    }
    @PostMapping("klass")
    ResponseEntity<ResponseObject> createKlass(@RequestBody KlassDto klassDto){
        Optional<Klass> check = klassRepository.findKlassByName(klassDto.getName());
        if (check.isPresent())
            throw new MyException(ErrorCode.DUPLICATE_EMAIL);

        Klass klass = new Klass();
        klass.setName(klassDto.getName());

        try {
            klassRepository.save(klass);
            return ResponseEntity
                    .status(201)
                    .body(new ResponseObject("Success","Create new class success",klassDto));
        }catch (Exception e){
            throw new MyException(ErrorCode.SERVER_ERROR);
        }
    }
}
