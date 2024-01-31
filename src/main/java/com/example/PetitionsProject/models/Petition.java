package com.example.PetitionsProject.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "petitions")
public class Petition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String heading;
    private String fullText;
    private int votes;
    private String createdOn;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by", nullable = false)
    private AppUser createdBy;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "petition_voters",
            joinColumns = @JoinColumn(name = "petition_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> votedBy = new ArrayList<>();

    public String getCreatedOn() {
        if (createdOn == null) {
            createdOn = formatCreatedOn();
        }
        return createdOn;
    }

    private String formatCreatedOn() {
        LocalDateTime createdOn = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return createdOn.format(formatter);
    }
    public void vote() {
        votes++;
    }
    public void unvote() {
        votes--;
    }
    public String getHeading() {
        return heading;
    }
    public List<AppUser> getVotedBy() {
        return votedBy;
    }
    public List<AppUser> setVotedBy(AppUser user) {
        votedBy.add(user);
        return votedBy;
    }
    public List<AppUser> deleteVoter(AppUser voter) {
        votedBy.remove(voter);
        return votedBy;
    }

}
