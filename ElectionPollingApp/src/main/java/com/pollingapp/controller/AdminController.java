package com.pollingapp.controller;

import com.pollingapp.model.Candidate;
import com.pollingapp.service.CandidateService;
import com.pollingapp.service.ElectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CandidateService candidateService;
    private final ElectionService electionService;

    public AdminController(CandidateService candidateService, ElectionService electionService) {
        this.candidateService = candidateService;
        this.electionService = electionService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("candidates", candidateService.listAll());
        model.addAttribute("control", electionService.getControl());
        return "admin/dashboard";
    }

    @GetMapping("/candidates/add")
    public String addForm(Model model) {
        model.addAttribute("candidate", new Candidate());
        return "admin/addCandidate";
    }

    @PostMapping("/candidates/add")
    public String addCandidate(@ModelAttribute Candidate candidate) {
        candidateService.addCandidate(candidate);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/candidates/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        candidateService.findById(id).ifPresent(c -> model.addAttribute("candidate", c));
        return "admin/editCandidate";
    }

    @PostMapping("/candidates/edit/{id}")
    public String editCandidate(@PathVariable Long id, @ModelAttribute Candidate candidate) {
        candidateService.updateCandidate(id, candidate);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/candidates/delete/{id}")
    public String deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/control/toggle")
    public String toggleVoting(@RequestParam boolean enabled) {
        electionService.setVotingEnabled(enabled);
        return "redirect:/admin/dashboard";
    }
}
