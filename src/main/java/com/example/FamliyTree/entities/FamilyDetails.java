package com.example.FamliyTree.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "family_details")
@Data
public class FamilyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String familyName;

    private String gothram;

    @OneToMany(mappedBy = "familyDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Person> members;
}