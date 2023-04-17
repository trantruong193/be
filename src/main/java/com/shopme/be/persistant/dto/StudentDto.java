package com.shopme.be.persistant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shopme.be.persistant.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto implements Serializable {
    private Long id;
    private String firstname;
    private String lastname;

    private String email;
    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String avatar;
    private String[] hobbies;

    public StudentDto(Student student){
        this.id = student.getId();
        this.firstname = student.getFirstname();
        this.lastname = student.getLastname();
        this.gender = student.getGender();
        this.email = student.getEmail();
        this.birthday = student.getBirthday();
        this.avatar = student.getAvatar();
        if (student.getHobbies() != null){
            int length = student.getHobbies().length();
            this.hobbies = student.getHobbies().substring(1,length-1).replace(" ","").split(",");
        }else {
            this.hobbies = new String[0];
        }

    }
}