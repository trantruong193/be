package com.shopme.be.persistant.dto;

import com.shopme.be.persistant.model.User;
import com.shopme.be.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    private Long id;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @NotEmpty
    @Email
    private String email;
    private String nickname;
    @NotEmpty
    private String password;
    private Date createAt;
    private Date updatedAt;
    private String address;
    private Date birthday;
    private String gender;
    private String avatar;
    private boolean tick;
    private Role role;

    public UserDto(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.createAt = user.getCreateAt();
        this.updatedAt = user.getUpdatedAt();
        this.address = user.getAddress();
        this.birthday = user.getBirthday();
        this.gender = user.getGender();
        this.avatar = user.getAvatar();
        this.tick = user.isTick();
        this.role = user.getRole();
    }
}