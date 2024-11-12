package com.example.FamliyTree.repositories;
import com.example.FamliyTree.entities.FamilyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FamilyDetailsRepository extends JpaRepository<FamilyDetails, Long> {
    Optional<FamilyDetails> findByFamilyName(String familyName);
}
