package com.pollingapp.controller;

import com.pollingapp.model.Role;
import com.pollingapp.model.User;
import com.pollingapp.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ------------------ Home Page ------------------
    @GetMapping({"/", "/home"})
    public String home() {
        return "home"; // home.html
    }

    // ------------------ Register ------------------
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // register.html
    }

    @PostMapping("/register")
    public String registerSubmit(
            @ModelAttribute User user,
            @RequestParam("selectedRole") String selectedRole,
            Model model) {

        Role role = selectedRole.equalsIgnoreCase("admin") ? Role.ROLE_ADMIN : Role.ROLE_VOTER;

        try {
            userService.registerUser(user.getUsername(), user.getPassword(), role);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        return "redirect:/login";
    }

    // ------------------ Login → Role-Based Redirect ------------------
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        String username = authentication.getName();
        Role role = userService.findByUsername(username).get().getRole();

        if (role == Role.ROLE_ADMIN)
            return "redirect:/admin/dashboard";

        return "redirect:/vote/list";
    }
}
