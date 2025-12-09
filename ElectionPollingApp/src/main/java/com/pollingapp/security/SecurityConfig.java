package com.pollingapp.security;

import com.pollingapp.model.Role;
import com.pollingapp.model.User;
import com.pollingapp.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import java.util.Optional;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    // ---------------------- PASSWORD ENCODER ----------------------
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCrypt hashing
    }

    // ---------------------- USER DETAILS SERVICE ----------------------
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<User> userOpt = userService.findByUsername(username);

            if (userOpt.isEmpty()) {
                throw new UsernameNotFoundException("User not found: " + username);
            }

            User user = userOpt.get();

            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority(user.getRole().name());

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    List.of(authority)
            );
        };
    }

    // ---------------------- SECURITY FILTER CHAIN ----------------------
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // disable for development; enable for production with config

            .authorizeHttpRequests(auth -> auth
                // Public pages
                .requestMatchers("/", "/home", "/register", "/login",
                        "/css/**", "/js/**", "/images/**").permitAll()

                // Admin-only routes
                .requestMatchers("/admin/**").hasAuthority(Role.ROLE_ADMIN.name())

                // Voter-only routes
                .requestMatchers("/vote/**").hasAuthority(Role.ROLE_VOTER.name())

                // Everything else requires login
                .anyRequest().authenticated()
            )

            // ------------------- LOGIN CONFIG -------------------
            .formLogin(form -> form
                .loginPage("/login")       // custom login page
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true) // send to role-based redirect
                .permitAll()
            )

            // ------------------- LOGOUT CONFIG -------------------
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            );

        return http.build();
    }
}
