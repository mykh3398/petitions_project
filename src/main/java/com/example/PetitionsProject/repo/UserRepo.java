package com.example.PetitionsProject.repo;

import com.example.PetitionsProject.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String userName);
    AppUser findFirstByUsername(String username);

}
