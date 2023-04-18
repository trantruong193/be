package com.shopme.be.persistant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "students")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstname",nullable = false)
    private String firstname;
    @Column(name = "lastname",nullable = false)
    private String lastname;
    @Column(name = "gender",nullable = false)
    private String gender;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "hobbies")
    private String hobbies;
    @Column(name = "status")
    private boolean status;
}
