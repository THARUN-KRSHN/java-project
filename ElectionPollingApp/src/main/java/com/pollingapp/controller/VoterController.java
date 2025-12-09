package com.pollingapp.controller;

import com.pollingapp.service.CandidateService;
import com.pollingapp.service.ElectionService;
import com.pollingapp.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vote")
public class VoterController {

    private final CandidateService candidateService;
    private final UserService userService;
    private final ElectionService electionService;

    public VoterController(CandidateService candidateService, UserService userService, ElectionService electionService) {
        this.candidateService = candidateService;
        this.userService = userService;
        this.electionService = electionService;
    }

    // ------------------ List Candidates ------------------
    @GetMapping("/list")
    public String listCandidates(Model model, Authentication auth) {

        model.addAttribute("candidates", candidateService.listAll());
        model.addAttribute("control", electionService.getControl());
        model.addAttribute("username", auth.getName());

        return "voter/list"; // voter/list.html
    }

    // ------------------ Vote ------------------
    @PostMapping("/cast/{candidateId}")
    public String castVote(
            @PathVariable Long candidateId,
            Authentication auth,
            Model model) {

        String username = auth.getName();

        // Check if voting is enabled
        if (!electionService.getControl().isVotingEnabled()) {
            model.addAttribute("error", "Voting is currently disabled.");
            return "voter/result";
        }

        // Prevent double voting
        if (userService.hasUserVoted(username)) {
            model.addAttribute("error", "You have already voted!");
            return "voter/result";
        }

        // Record vote
        candidateService.incrementVote(candidateId);
        userService.markUserVoted(username);

        // Auto logout after voting
        return "redirect:/logout";
    }
}
