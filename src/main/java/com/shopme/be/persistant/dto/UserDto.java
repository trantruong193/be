package com.shopme.be.persistant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shopme.be.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    private Long id;
    @NotBlank(message = "firstname is required")
    private String firstname;
    @NotBlank(message = "lastname is required")
    private String lastname;
    @Email(message = "not email format")
    private String email;
    private String nickname;
    private Date createAt;
    private Date updatedAt;
    @NotBlank(message = "address is required")
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String gender;
    private String avatar;
    private boolean tick;
    private Role role;
}