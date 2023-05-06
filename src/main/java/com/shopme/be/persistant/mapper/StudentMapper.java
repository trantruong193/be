package com.shopme.be.persistant.mapper;

import com.shopme.be.persistant.dto.KlassDto;
import com.shopme.be.persistant.dto.StudentDto;
import com.shopme.be.persistant.model.Student;

public class StudentMapper {
    public static StudentDto toDto(Student entity){

        StudentDto result = new StudentDto();
        result.setId(entity.getId());
        result.setFirstname(entity.getFirstname());
        result.setLastname(entity.getLastname());
        result.setGender(entity.getGender());
        result.setEmail(entity.getEmail());
        result.setBirthday(entity.getBirthday());
        result.setAvatar(entity.getAvatar());
        result.setStatus(entity.isStatus());
        if (entity.getKlass() != null){
            result.setKlass(new KlassDto(entity.getKlass().getId(),entity.getKlass().getName()));
        }
        if (entity.getHobbies() != null){
            int length = entity.getHobbies().length();
            result.setHobbies(entity.getHobbies().substring(1,length-1).replace(" ","").split(","));
        }else {
            result.setHobbies(new String[0]);
        }

        return result;
    }
}
