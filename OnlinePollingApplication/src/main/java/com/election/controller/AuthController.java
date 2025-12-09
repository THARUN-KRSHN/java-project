package com.election.controller;
import com.election.entity.Role;
import com.election.entity.User;
import com.election.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;

    @GetMapping("/login") public String login() { return "login"; }
    @GetMapping("/register") public String register() { return "register"; }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_VOTER);
        userRepo.save(user);
        return "redirect:/login?success";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth) {
        String role = auth.getAuthorities().iterator().next().getAuthority();
        return role.equals("ROLE_ADMIN") ? "redirect:/admin/dashboard" : "redirect:/voter/dashboard";
    }
}