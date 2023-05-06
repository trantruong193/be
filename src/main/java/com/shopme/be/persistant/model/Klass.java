package com.shopme.be.persistant.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "classes")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Klass {
    @Id
    @SequenceGenerator(
            name = "classes_id_sequence",
            sequenceName = "classes_id_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "classes_id_sequence"
    )
    @Column(name = "class_id")
    private Long id;
    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "klass")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Student> students;
}
