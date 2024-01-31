package com.example.PetitionsProject.repo;

import com.example.PetitionsProject.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}