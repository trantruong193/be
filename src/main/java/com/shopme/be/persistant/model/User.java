package com.shopme.be.persistant.model;

import com.shopme.be.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname",length = 50,nullable = false)
    private String firstname;

    @Column(name = "lastname",length = 50,nullable = false)
    private String lastname;

    @Column(name = "email",length = 100,nullable = false,unique = true,updatable = false)
    private String email;

    @Column(name = "nickname",length = 100)
    private String nickname;

    @Column(name = "password",length = 100,nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "create_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "address")
    private String address;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "gender")
    private String gender;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "tick")
    private boolean tick;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
