package com.example.FamliyTree.controllers;

import com.example.FamliyTree.entities.*;
import com.example.FamliyTree.repositories.PersonRepository;
import com.example.FamliyTree.services.FamilyTreeService;
import com.example.FamliyTree.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/family-tree")
@RequiredArgsConstructor
public class FamilyTreeController {
    private final FamilyTreeService familyTreeService;
    private final PersonRepository personRepository;
    @Autowired
    private final PersonService personService;

    @PostMapping("/families")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FamilyDetails> createFamily(@RequestBody FamilyDetails familyDetails) {
        return ResponseEntity.ok(familyTreeService.createFamily(familyDetails));
    }

    @GetMapping("/families")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<FamilyDetails>> getAllFamilies() {
        return ResponseEntity.ok(familyTreeService.getAllFamilies());
    }

    @PostMapping("/persons")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        return ResponseEntity.ok(familyTreeService.addPerson(person));
    }

    @PutMapping("/persons/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Person> updatedPerson(@PathVariable Long id, @RequestBody Person person) {
        person.setId(id);
        System.out.println(id);
        return ResponseEntity.ok(familyTreeService.updatedPerson(person));
    }

    @GetMapping("/families/{familyId}/members")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Person>> getFamilyMembers(@PathVariable Long familyId) {
        return ResponseEntity.ok(familyTreeService.getFamilyMembers(familyId));
    }

    @GetMapping("/persons/{parentId}/children")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Person>> getChildren(@PathVariable Long parentId) {
        return ResponseEntity.ok(familyTreeService.getChildren(parentId));
    }

    @DeleteMapping("/deletefamily/{familyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteFamliy(@PathVariable Long familyId) {
        return ResponseEntity.ok(familyTreeService.deleteFamily(familyId));
    }

    @DeleteMapping("/deletePersons/{Id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePerson(@PathVariable Long Id) {
        return ResponseEntity.ok(familyTreeService.deletePerson(Id));
    }

    @GetMapping("/families/{familyId}/members/withspouces")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Person>> fetchFamilyDetailsWithSpouses(@PathVariable Long familyId) {
        return ResponseEntity.ok(familyTreeService.getFamilyMembers(familyId));
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        // Extract spouses from the request body
        Set<Person> spouses = person.getSpouses();
        person.setSpouses(new HashSet<>()); // Avoid infinite recursion during deserialization

        // Update the person and add spouses
        Person updatedPerson = PersonService.addSpouses(person, spouses);

        return ResponseEntity.ok(updatedPerson);
    }


}