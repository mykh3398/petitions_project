package com.example.PetitionsProject.service;

import com.example.PetitionsProject.dto.RegistrationDto;
import com.example.PetitionsProject.models.AppUser;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    AppUser findByUsername(String username);

}