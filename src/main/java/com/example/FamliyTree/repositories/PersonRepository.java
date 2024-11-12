package com.example.FamliyTree.repositories;
import com.example.FamliyTree.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByFamilyDetailsId(Long familyId);
    List<Person> findByMotherId(Long motherId);
    List<Person> findByFatherId(Long fatherId);


}