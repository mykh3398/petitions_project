package com.example.PetitionsProject.controllers;

import com.example.PetitionsProject.dto.PetitionDto;
import com.example.PetitionsProject.models.AppUser;
import com.example.PetitionsProject.models.Petition;
import com.example.PetitionsProject.security.SecurityUtil;
import com.example.PetitionsProject.service.PetitionService;
import com.example.PetitionsProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PetitionController {
    private PetitionService petitionService;
    private UserService userService;


    @Autowired
    public PetitionController(PetitionService petitionService, UserService userService) {
        this.petitionService = petitionService;
        this.userService = userService;
    }

    @GetMapping("/petitions")
    public String listPetitions(Model model) {
        AppUser user = new AppUser();
        List<PetitionDto> petitions = petitionService.findAllPetitions();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("petitions", petitions);
        return "petitions-list";
    }

    @GetMapping("/petitions/add")
    public String addPet(Model model) {
        Petition petition = new Petition();
        AppUser user = new AppUser();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("petition", petition);
        return "petition-add";
    }

    @GetMapping("/petitions/{petId}/delete")
    public String delete(@PathVariable("petId") Long petId, Model model) {
        AppUser user = new AppUser();
        petitionService.delete(petId);
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        return "redirect:/petitions";
    }

    @GetMapping("/petitions/{petId}")
    public String details(@PathVariable("petId") Long petId, Model model) {
        AppUser user = new AppUser();
        PetitionDto petitionDto = petitionService.findPetitionById(petId);
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("petition", petitionDto);
        return "petition-details";
    }

    @GetMapping("/petitions/{petId}/vote")
    public String vote(@PathVariable("petId") Long petId, Model model) {
        AppUser user = new AppUser();
        PetitionDto petitionDto = petitionService.findPetitionById(petId);
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            List<AppUser> voters = petitionDto.getVotedBy();
            for (AppUser voter : voters) {
                if (voters != null && voter.equals(user)) {
                    return "redirect:/petitions/{petId}";
                }
            }
            model.addAttribute("user", user);
            model.addAttribute("petition", petitionDto);
            petitionService.vote(petitionDto, user);

        }
        model.addAttribute("user", user);
        model.addAttribute("petition", petitionDto);
        return "redirect:/petitions/{petId}";
    }

    @GetMapping("/petitions/{petId}/unvote")
    public String unvote(@PathVariable("petId") Long petId, Model model) {
        AppUser user = new AppUser();
        PetitionDto petitionDto = petitionService.findPetitionById(petId);
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            List<AppUser> voters = petitionDto.getVotedBy();
            for (AppUser voter : voters) {
                if (user.equals(voter)) {
                    model.addAttribute("user", user);
                    model.addAttribute("petition", petitionDto);
                    petitionService.unvote(petitionDto, user);
                    return "redirect:/petitions/{petId}";
                }
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("petition", petitionDto);
        return "redirect:/petitions/{petId}";
    }

    @PostMapping("/petitions/add")
    public String savePetition(@Valid @ModelAttribute("petition") PetitionDto petitionDto,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("petition", petitionDto);
            return "petition-add";
        }
        petitionService.savePetition(petitionDto);
        return "redirect:/petitions";
    }
}
/*
    @GetMapping("/petitions/{petId}/edit")
    public String editPetition(@PathVariable("petId") Long petId, Model model){
        PetitionDto petition = petitionService.findPetitionById(petId);
        model.addAttribute("petition", petition);
        return "petition-edit";
    }
*/

/*
    @PostMapping("/petitions/{petId}/edit")
    public String updatePetition(@PathVariable("petId") Long petId,
                                 @Valid @ModelAttribute("petition") PetitionDto petition,
                                 BindingResult result, Model model){
        if(result.hasErrors()) {
            model.addAttribute("petition", petition);
            return "petition-edit";
        }
        petition.setId(petId);
        petitionService.updatePetition(petition);
        return "redirect:/petitions";
    }

*/


