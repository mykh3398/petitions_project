package com.example.PetitionsProject.service.impl;

import com.example.PetitionsProject.dto.PetitionDto;
import com.example.PetitionsProject.models.AppUser;
import com.example.PetitionsProject.models.Petition;
import com.example.PetitionsProject.repo.PetitionRepo;
import com.example.PetitionsProject.repo.UserRepo;
import com.example.PetitionsProject.security.SecurityUtil;
import com.example.PetitionsProject.service.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.PetitionsProject.mapper.PetitionMapper.mapToPetition;
import static com.example.PetitionsProject.mapper.PetitionMapper.mapToPetitionDto;

@Service
public class PetitionServiceImpl implements PetitionService {
    private PetitionRepo petitionRepo;
    private UserRepo userRepo;
    @Autowired
    public PetitionServiceImpl(PetitionRepo petitionRepo, UserRepo userRepo) {
        this.userRepo = userRepo;
        this.petitionRepo = petitionRepo;
    }

    @Override
    public List<PetitionDto> findAllPetitions() {
        List<Petition> petitions = petitionRepo.findAll();
        return petitions.stream().map((petition) -> mapToPetitionDto(petition)).collect(Collectors.toList());
    }

    @Override
    public Petition savePetition(PetitionDto petitionDto) {
        String username = SecurityUtil.getSessionUser();
        AppUser user = userRepo.findByUsername(username);
        Petition petition = mapToPetition(petitionDto);
        petition.setCreatedBy(user);
        return petitionRepo.save(petition);
    }

    @Override
    public PetitionDto findPetitionById(Long petId) {
        Petition petition = petitionRepo.findById(petId).get();
        return mapToPetitionDto(petition);
    }

    @Override
    public void delete(Long petId) {
        petitionRepo.deleteById(petId);
    }

    @Override
    public void vote(PetitionDto petitionDto, AppUser user) {
        Petition petition = mapToPetition(petitionDto);
        List<AppUser> votedBy = petition.getVotedBy();
        if (votedBy.contains(user)) {
            throw new IllegalStateException("User has already voted for this petition");
        }
        petition.setVotedBy(user);
        petition.vote();
        petitionRepo.save(petition);
    }

    @Override
    public void unvote(PetitionDto petitionDto, AppUser voter) {
        Petition petition = mapToPetition(petitionDto);
        List<AppUser> votedBy = petition.getVotedBy();
        if (!votedBy.contains(voter)) {
            throw new IllegalStateException("User hasn't voted for this petition");
        }
        petition.unvote();
        petition.deleteVoter(voter);
        petitionRepo.save(petition);
    }

    @Override
    public List<PetitionDto> searchPetition(String query) {
        List<Petition> petitions = petitionRepo.searchPetition(query);
        return petitions.stream().map(petition -> mapToPetitionDto(petition)).collect(Collectors.toList());
    }

}
