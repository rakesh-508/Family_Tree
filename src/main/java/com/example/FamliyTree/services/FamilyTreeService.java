package com.example.FamliyTree.services;

import com.example.FamliyTree.entities.FamilyDetails;
import com.example.FamliyTree.entities.Person;
import com.example.FamliyTree.repositories.FamilyDetailsRepository;
import com.example.FamliyTree.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FamilyTreeService {
    private final FamilyDetailsRepository familyDetailsRepository;
    private final PersonRepository personRepository;

    public FamilyDetails createFamily(FamilyDetails familyDetails) {
        return familyDetailsRepository.save(familyDetails);
    }


//    @Autowired
//    public FamilyTreeService(PersonRepository personRepository,
//                             FamilyDetailsRepository familyDetailsRepository) {
//        this.personRepository = personRepository;
//        this.familyDetailsRepository = familyDetailsRepository;
//    }

    public List<FamilyDetails> getAllFamilies() {
        return familyDetailsRepository.findAll();
    }

    public Person addPerson(Person person) {
        return personRepository.save(person);
    }


    public Person updatedPerson(Person person) {
        System.out.println(person+"This is the exact reason for form the data.");
        return personRepository.save(person);
    }
    public List<Person> getFamilyMembers(Long familyId) {
        return personRepository.findByFamilyDetailsId(familyId);
    }

    public List<Person> getChildren(Long parentId) {
        List<Person> maternalChildren = personRepository.findByMotherId(parentId);
        List<Person> paternalChildren = personRepository.findByFatherId(parentId);
        Set<Person> allChildren = new HashSet<>();
        allChildren.addAll(maternalChildren);
        allChildren.addAll(paternalChildren);
        return new ArrayList<>(allChildren);
    }

    public String deleteFamily(Long familyId) {
        familyDetailsRepository.deleteById(familyId);
        System.out.println("Family repository: " + familyDetailsRepository);
        return "The Family with ID " + familyId + " has been deleted";
    }

    public String deletePerson(Long Id) {
        personRepository.deleteById(Id);
        System.out.println("Person repository: " + familyDetailsRepository);
        return "The Person with ID " + Id + " has been deleted";
    }

    public FamilyDetails fetchFamilyDetailsWithSpouses(Long familyId) {
        FamilyDetails family = familyDetailsRepository.findById(familyId).orElseThrow();

        // Utilize a query to fetch family members (including spouses)
        List<Person> familyMembersWithSpouses = personRepository.findByFamilyDetailsId(familyId);

        // Manually set the members (including spouses) on the family object
        // NOTE: This is a temporary solution. For large datasets, consider a more efficient approach.
        family.setMembers(familyMembersWithSpouses);

        return family;
    }

    public Person addSpouses(Person person, Set<Person> spouses) {
        // Retrieve the person entity
        Person existingPerson = personRepository.findById(person.getId())
                .orElseThrow();

        // Clear existing spouses to avoid duplicates
        existingPerson.setSpouses(new HashSet<>());

        // Add new spouses
        spouses.forEach(spouse -> {
            // Retrieve or create spouse entity
            Person existingSpouse = personRepository.findById(spouse.getId())
                    .orElseGet(() -> personRepository.save(spouse));

            // Update spouses collection
            existingPerson.getSpouses().add(existingSpouse);
            existingSpouse.getSpouses().add(existingPerson); // Symmetrical relationship
        });

        // Save changes
        return personRepository.save(existingPerson);
    }
}