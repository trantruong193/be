package com.shopme.be.persistant.model;

import com.shopme.be.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
@Builder
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50,nullable = false)
    private String firstname;

    @Column(length = 50,nullable = false)
    private String lastname;

    @Column(length = 100,nullable = false,unique = true)
    private String email;

    @Column(length = 100)
    private String nickname;

    @Column(length = 100,nullable = false)
    private String password;

    @CreationTimestamp
    private Date createAt;

    @UpdateTimestamp
    private Date updatedAt;

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String gender;

    private String avatar;

    private boolean tick;

    @Enumerated(EnumType.STRING)
    private Role role;

}
