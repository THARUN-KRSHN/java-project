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

    // ------------------ Dashboard ------------------
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("candidates", candidateService.listAll());
        model.addAttribute("control", electionService.getControl());
        return "admin/dashboard"; // /templates/admin/dashboard.html
    }

    // ------------------ Add Candidate ------------------
    @GetMapping("/candidates/add")
    public String addCandidateForm(Model model) {
        model.addAttribute("candidate", new Candidate());
        return "admin/addCandidate";
    }

    @PostMapping("/candidates/add")
    public String addCandidateSubmit(@ModelAttribute Candidate candidate) {
        candidateService.addCandidate(candidate);
        return "redirect:/admin/dashboard";
    }

    // ------------------ Edit Candidate ------------------
    @GetMapping("/candidates/edit/{id}")
    public String editCandidateForm(@PathVariable Long id, Model model) {
        model.addAttribute("candidate", candidateService.findById(id).orElseThrow());
        return "admin/editCandidate";
    }

    @PostMapping("/candidates/edit/{id}")
    public String editCandidate(@PathVariable Long id, @ModelAttribute Candidate update) {
        candidateService.updateCandidate(id, update);
        return "redirect:/admin/dashboard";
    }

    // ------------------ Delete Candidate ------------------
    @PostMapping("/candidates/delete/{id}")
    public String deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return "redirect:/admin/dashboard";
    }

    // ------------------ Enable / Disable Voting ------------------
    @PostMapping("/toggle-voting")
    public String toggleVoting(@RequestParam boolean enabled) {
        electionService.setVotingEnabled(enabled);
        return "redirect:/admin/dashboard";
    }
}
