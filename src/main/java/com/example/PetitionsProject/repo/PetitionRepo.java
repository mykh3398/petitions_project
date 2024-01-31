package com.example.PetitionsProject.repo;

import com.example.PetitionsProject.models.Petition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PetitionRepo extends JpaRepository<Petition, Long> {
    Optional<Petition> findByName(String url);
    @Query("SELECT c from Petition c WHERE c.name LIKE CONCAT('%', :query, '%')")
    List<Petition> searchPetition(String query);

}