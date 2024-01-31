package com.example.PetitionsProject.dto;

import com.example.PetitionsProject.models.AppUser;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PetitionDto {
    private long id;
    @NotEmpty(message = "Title shouldn't be empty")
    private String name;
    @NotEmpty(message = "Heading shouldn't be empty")
    private String heading;
    @NotEmpty(message = "Textarea shouldn't be empty")
    private String fullText;
    private AppUser createdBy;
    private List<AppUser> votedBy;
    private int votes;
    private String createdOn;
    private LocalDateTime updatedOn;


}