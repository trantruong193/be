package com.shopme.be.service.impl;

import com.shopme.be.exception.ErrorCode;
import com.shopme.be.exception.MyException;
import com.shopme.be.persistant.dto.StudentDto;
import com.shopme.be.persistant.mapper.StudentMapper;
import com.shopme.be.persistant.model.Klass;
import com.shopme.be.persistant.model.Student;
import com.shopme.be.repository.KlassRepository;
import com.shopme.be.repository.StudentRepository;
import com.shopme.be.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final KlassRepository klassRepository;
    @Override
    @Transactional
    public StudentDto add(StudentDto studentDto) {
        // check if email has been used
        log.info("Start {}",Thread.currentThread().getName());
        Optional<Student> checkEmail = studentRepository.findStudentByEmail(studentDto.getEmail());
        if (checkEmail.isPresent()){
            throw new MyException(ErrorCode.DUPLICATE_EMAIL);
        }
        // creat new student
        try {
            Student newStudent = Student.builder()
                    .firstname(studentDto.getFirstname())
                    .lastname(studentDto.getLastname())
                    .gender(studentDto.getGender())
                    .email(studentDto.getEmail())
                    .birthday(studentDto.getBirthday())
                    .avatar(studentDto.getAvatar())
                    .status(true)
                    .build();
            if (studentDto.getKlass().getId() != null){
                Optional<Klass> klass = klassRepository.findById(studentDto.getKlass().getId());
                if (klass.isEmpty()){
                    throw new MyException(ErrorCode.INVALID_INFORMATION);
                }
                newStudent.setKlass(klass.get());
            }
            if (studentDto.getHobbies() != null && studentDto.getHobbies().length != 0)
                newStudent.setHobbies(Arrays.toString(studentDto.getHobbies()));

            Student saved = studentRepository.save(newStudent);
            log.info("Create new student {}", newStudent);
            return StudentMapper.toDto(saved);

        }catch (Exception e){
            throw new MyException(ErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public List<StudentDto> findAll() {
        try {
            List<Student> students = studentRepository.findAll();
            return students.stream().map(StudentMapper::toDto).toList();
        }catch (Exception e){
            throw new MyException(ErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public StudentDto findById(Long id) {
        long start = System.currentTimeMillis();
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()){
            throw new MyException(ErrorCode.INVALID_INFORMATION);
        }
        long end = System.currentTimeMillis();
        log.info(String.format("%1$s Get students detail in %2$s ms",Thread.currentThread().getName(),end-start));
        return StudentMapper.toDto(student.get());
    }

    @Override
    @Transactional
    public StudentDto update(StudentDto studentDto) {
        // check student is existed
        Optional<Student> find = studentRepository.findById(studentDto.getId());
        if (find.isEmpty()){
            throw new MyException(ErrorCode.INVALID_INFORMATION);
        }
        // get student and set new info
        Student found = find.get();

        if (!studentDto.getFirstname().isEmpty() && studentDto.getFirstname() != null)
            found.setFirstname(studentDto.getFirstname());
        if (!studentDto.getLastname().isEmpty() && studentDto.getLastname() != null)
            found.setLastname(studentDto.getLastname());
        if (studentDto.getBirthday() != null)
            found.setBirthday(studentDto.getBirthday());
        if (!studentDto.getGender().isEmpty() && studentDto.getGender() != null)
            found.setGender(studentDto.getGender());

        if (studentDto.getKlass().getId() != null){
            Optional<Klass> klass = klassRepository.findById(studentDto.getKlass().getId());
            if (klass.isEmpty()){
                throw new MyException(ErrorCode.INVALID_INFORMATION);
            }
            found.setKlass(klass.get());
        }
        if (studentDto.getHobbies() != null && studentDto.getHobbies().length>0)
            found.setHobbies(Arrays.toString(studentDto.getHobbies()));
        if (!studentDto.getAvatar().isEmpty() && studentDto.getAvatar() != null)
            found.setAvatar(studentDto.getAvatar());
        found.setStatus(studentDto.isStatus());

        try {
            Student saved =  studentRepository.save(found);
            log.info("Update student {}",saved);
            return StudentMapper.toDto(saved);
        }catch (Exception e){
            throw new MyException(ErrorCode.SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public boolean remove(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()){
            throw new MyException(ErrorCode.INVALID_INFORMATION);
        }
        student.get().setStatus(false);
        return true;
    }

    @Override
    public Page<StudentDto> getAll(Pageable pageable) {
        long start = System.currentTimeMillis();
        Page<Student> students = studentRepository.findAll(pageable);
        long end = System.currentTimeMillis();
        log.info(String.format("%1$s Get all students in %2$s ms",Thread.currentThread().getName(),end-start));
        return students.map(StudentMapper::toDto);
    }

    @Override
    public StudentDto getByEmail(String email) {
        Optional<Student> student = studentRepository.findStudentByEmail(email);
        if (student.isEmpty()){
            throw new MyException(ErrorCode.INVALID_INFORMATION);
        }
        return StudentMapper.toDto(student.get());
    }
}
