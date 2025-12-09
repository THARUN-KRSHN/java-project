package com.election.controller;
import com.election.entity.ElectionMetadata;
import com.election.entity.User;
import com.election.repository.UserRepository;
import com.election.service.SystemService;
import com.election.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/voter")
public class VoterController {
    @Autowired private VotingService votingService;
    @Autowired private SystemService systemService;
    @Autowired private UserRepository userRepo;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        User user = userRepo.findByUsername(auth.getName()).get();
        ElectionMetadata meta = systemService.getMetadata();

        model.addAttribute("user", user);
        model.addAttribute("candidates", votingService.getAllCandidates());
        model.addAttribute("votingEnabled", meta.isVotingEnabled());
        model.addAttribute("resultsPublished", meta.isResultsPublished());
        
        return "voter-dashboard";
    }

    @PostMapping("/cast")
    public String castVote(@RequestParam Long candidateId, Authentication auth) {
        if(systemService.getMetadata().isVotingEnabled()) {
            votingService.castVote(candidateId, auth.getName());
        }
        return "redirect:/voter/dashboard";
    }
}