package com.example.PetitionsProject.service;

import com.example.PetitionsProject.dto.PetitionDto;
import com.example.PetitionsProject.models.AppUser;
import com.example.PetitionsProject.models.Petition;

import java.util.List;

public interface PetitionService {
        List<PetitionDto> findAllPetitions();
        Petition savePetition(PetitionDto petitionDto);
        PetitionDto findPetitionById(Long petId);
        void delete(Long petId);
        void vote(PetitionDto petitionDto, AppUser user);
        void unvote(PetitionDto petitionDto, AppUser user);
        List<PetitionDto> searchPetition(String query);
}