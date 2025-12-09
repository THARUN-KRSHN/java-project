package com.pollingapp.controller;

import com.pollingapp.model.User;
import com.pollingapp.model.Role;
import com.pollingapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/home"})
    public String home() {
        return "home"; // Thymeleaf template: home.html
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // register.html
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user, @RequestParam String role, Model model) {
        Role r = "admin".equalsIgnoreCase(role) ? Role.ROLE_ADMIN : Role.ROLE_VOTER;
        try {
            userService.registerUser(user.getUsername(), user.getPassword(), r);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        // after login, redirect logic can be improved by checking role and returning corresponding dashboard
        return "dashboard"; // dashboard.html
    }
}
