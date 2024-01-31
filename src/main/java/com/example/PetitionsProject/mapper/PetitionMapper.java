package com.example.PetitionsProject.mapper;

import com.example.PetitionsProject.dto.PetitionDto;
import com.example.PetitionsProject.models.Petition;

public class PetitionMapper {
    public static Petition mapToPetition(PetitionDto petition) {
        Petition petitionDto = Petition.builder()
                .id(petition.getId())
                .name(petition.getName())
                .fullText(petition.getFullText())
                .createdBy(petition.getCreatedBy())
                .votedBy(petition.getVotedBy())
                .createdOn(petition.getCreatedOn())
                .votes(petition.getVotes())
                .heading(petition.getHeading())
                .build();
        return petitionDto;
    }

    public static PetitionDto mapToPetitionDto(Petition petition){
        PetitionDto petitionDto = PetitionDto.builder()
                .id(petition.getId())
                .name(petition.getName())
                .fullText(petition.getFullText())
                .votes(petition.getVotes())
                .createdBy(petition.getCreatedBy())
                .votedBy(petition.getVotedBy())
                .createdOn(petition.getCreatedOn())
                .heading(petition.getHeading())
                .build();
        return petitionDto;
    }

}
