package com.shopme.be.persistant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto implements Serializable {
    private Long id;
    @NotBlank(message = "firstname is required")
    private String firstname;
    @NotBlank(message = "lastname is required")
    private String lastname;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "gender is required")
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "birthday is required")
    private Date birthday;
    private String avatar;
    private String[] hobbies;
    private boolean status;
    private KlassDto klass;
}