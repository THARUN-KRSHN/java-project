package com.pollingapp.security;

import com.pollingapp.model.Role;
import com.pollingapp.service.UserService;
import com.pollingapp.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import java.util.Optional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<User> userOpt = userService.findByUsername(username);
            if (userOpt.isEmpty()) throw new UsernameNotFoundException("User not found");
            User u = userOpt.get();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(u.getRole().name());
            return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), List.of(authority));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // bcrypt
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // enable for production with proper config
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/home", "/register", "/css/**", "/js/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority(Role.ROLE_ADMIN.name())
                .requestMatchers("/vote/**").hasAuthority(Role.ROLE_VOTER.name())
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/dashboard", true)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .permitAll()
            );
        return http.build();
    }
}
