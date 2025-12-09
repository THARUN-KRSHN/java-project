package com.election.controller;
import com.election.entity.ElectionMetadata;
import com.election.service.SystemService;
import com.election.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired private VotingService votingService;
    @Autowired private SystemService systemService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        ElectionMetadata meta = systemService.getMetadata();
        model.addAttribute("candidates", votingService.getAllCandidates());
        model.addAttribute("totalVotes", votingService.getTotalVotes());
        model.addAttribute("votingEnabled", meta.isVotingEnabled());
        return "admin-dashboard";
    }

    @PostMapping("/addCandidate")
    public String addCandidate(@RequestParam String name, @RequestParam String party) {
        votingService.addCandidate(name, party);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/toggleVoting")
    public String toggleVoting() {
        boolean current = systemService.getMetadata().isVotingEnabled();
        systemService.toggleVoting(!current);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/publishResults")
    public String publishResults() {
        systemService.publishResults(true);
        return "redirect:/admin/dashboard";
    }
}