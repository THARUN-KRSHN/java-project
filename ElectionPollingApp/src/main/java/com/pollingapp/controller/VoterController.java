package com.pollingapp.controller;

import com.pollingapp.model.Candidate;
import com.pollingapp.model.User;
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

    @GetMapping("/list")
    public String listCandidates(Model model) {
        model.addAttribute("candidates", candidateService.listAll());
        model.addAttribute("control", electionService.getControl());
        return "voter/list";
    }

    @PostMapping("/vote/{candidateId}")
    public String vote(@PathVariable Long candidateId, Authentication authentication, Model model) {
        String username = authentication.getName();
        if (userService.hasUserVoted(username)) {
            model.addAttribute("error", "You have already voted.");
            return "voter/result";
        }
        if (!electionService.getControl().isVotingEnabled()) {
            model.addAttribute("error", "Voting is currently disabled.");
            return "voter/result";
        }
        candidateService.incrementVote(candidateId);
        userService.markUserVoted(username);
        model.addAttribute("message", "Vote recorded. You will be logged out.");
        return "redirect:/logout"; // log the voter out after voting
    }
}
