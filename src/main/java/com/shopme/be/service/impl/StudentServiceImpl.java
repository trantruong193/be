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
    public StudentDto add(StudentDto studentDto) {
        // check if email has been used
        Optional<Student> checkEmail = studentRepository.findStudentByEmail(studentDto.getEmail());
        if (checkEmail.isPresent()){
            throw new MyException(ErrorCode.DUPLICATE_EMAIL);
        }
        // creat new student
        Student newStudent = Student.builder()
                .firstname(studentDto.getFirstname())
                .lastname(studentDto.getLastname())
                .gender(studentDto.getGender())
                .email(studentDto.getEmail())
                .birthday(studentDto.getBirthday())
                .avatar(studentDto.getAvatar())
                .status(true)
                .build();
        // set klass if exist
        checkKlass(studentDto, newStudent);
        // set hobbies if exist
        if (studentDto.getHobbies() != null && studentDto.getHobbies().length != 0)
            newStudent.setHobbies(Arrays.toString(studentDto.getHobbies()));
        try {
            // save to database
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
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()){
            throw new MyException(ErrorCode.INVALID_INFORMATION);
        }
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
        found.setFirstname(studentDto.getFirstname());
        found.setLastname(studentDto.getLastname());
        found.setBirthday(studentDto.getBirthday());
        found.setGender(studentDto.getGender());
        // set klass if exist
        checkKlass(studentDto, found);
        // set hobbies if exist
        if (studentDto.getHobbies() != null && studentDto.getHobbies().length>0)
            found.setHobbies(Arrays.toString(studentDto.getHobbies()));
        // set avatar if exist
        if (!studentDto.getAvatar().isEmpty())
            found.setAvatar(studentDto.getAvatar());
        // set status
        found.setStatus(studentDto.isStatus());

        return StudentMapper.toDto(found);
    }

    private void checkKlass(StudentDto studentDto, Student found) {
        if (studentDto.getKlass() != null && studentDto.getKlass().getId() != null){
            Optional<Klass> klass = klassRepository.findById(studentDto.getKlass().getId());
            if (klass.isEmpty()){
                throw new MyException(ErrorCode.INVALID_INFORMATION);
            }
            found.setKlass(klass.get());
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
        log.info("Get students in: {}ms",System.currentTimeMillis()-start);
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
