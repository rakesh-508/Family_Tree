package com.example.FamliyTree.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "people")
@Data
//@JsonIgnoreProperties({"familyDetails", "mother", "father"})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;
    private Integer age;
    private String occupation;
    private Double salary;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private FamilyDetails familyDetails;

    @ManyToOne
    @JoinColumn(name = "mother_id")
    private Person mother;

    @ManyToOne
    @JoinColumn(name = "father_id")
    private Person father;


//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "person_spouses",
//            joinColumns = @JoinColumn(name = "person_id"),
//            inverseJoinColumns = @JoinColumn(name = "spouse_id")
//    )
//    private Set<Person> spouses = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "person_spouses",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "spouse_id")
    )
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Set<Person> spouses = new HashSet<>();

}