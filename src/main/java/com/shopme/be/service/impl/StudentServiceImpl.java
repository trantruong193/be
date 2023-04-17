package com.shopme.be.service.impl;

import com.shopme.be.persistant.dto.StudentDto;
import com.shopme.be.persistant.model.Student;
import com.shopme.be.repository.StudentRepository;
import com.shopme.be.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Page<StudentDto> getAll(Pageable pageable) {
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(StudentDto::new);
    }

    @Override
    public StudentDto getById(Long id) {

        Student student = studentRepository.findById(id).orElse(null);
        if (student != null){
            return new StudentDto(student);
        }else {
            return null;
        }

    }

    @Override
    public StudentDto getByEmail(String email) {
        Student student =  studentRepository.findStudentByEmail(email).orElse(null);
        if (student != null){
            return new StudentDto(student);
        }else {
            return null;
        }
    }

    @Override
    public boolean deleteStudent(Long id) {

        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()){
            try {
                studentRepository.deleteById(id);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public StudentDto saveStudent(StudentDto student) {
        try {
            Student newStudent = Student.builder()
                    .firstname(student.getFirstname())
                    .lastname(student.getLastname())
                    .gender(student.getGender())
                    .email(student.getEmail())
                    .birthday(student.getBirthday())
                    .avatar(student.getAvatar())
                    .hobbies(Arrays.toString(student.getHobbies()))
                    .build();

            Student newStudent1 = studentRepository.save(newStudent);

            return new StudentDto(newStudent1);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public StudentDto updateStudent(StudentDto student) {
        Student found = studentRepository.findById(student.getId()).orElse(null);
        if (found == null){
            return null;
        }
        if (!student.getEmail().equals(found.getEmail())){
            if (studentRepository.findStudentByEmail(student.getEmail()).isPresent()){
                return null;
            }
        }
        if (!student.getFirstname().isEmpty() && student.getFirstname() != null)
            found.setFirstname(student.getFirstname());
        if (!student.getLastname().isEmpty() && student.getLastname() != null)
            found.setLastname(student.getLastname());
        if (!student.getEmail().isEmpty() && student.getEmail() != null)
            found.setEmail(student.getEmail());
        if (student.getBirthday() != null)
            found.setBirthday(student.getBirthday());
        if (!student.getGender().isEmpty() && student.getGender() != null)
            found.setGender(student.getGender());
        if (student.getHobbies().length>0)
            found.setHobbies(Arrays.toString(student.getHobbies()));
        if (!student.getAvatar().isEmpty() && student.getAvatar() != null)
            found.setAvatar(student.getAvatar());
        try {
            studentRepository.save(found);
            return student;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
