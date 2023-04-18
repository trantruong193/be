package com.shopme.be.persistant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private boolean status;
}