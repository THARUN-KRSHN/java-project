package com.pollingapp.service.impl;

import com.pollingapp.model.User;
import com.pollingapp.model.Role;
import com.pollingapp.repository.UserRepository;
import com.pollingapp.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(String username, String rawPassword, Role role) {

        if (repo.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        String hashed = passwordEncoder.encode(rawPassword);

        User user = new User(username, hashed, role);
        return repo.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public void markUserVoted(String username) {
        repo.findByUsername(username).ifPresent(user -> {
            user.setHasVoted(true);
            repo.save(user);
        });
    }

    @Override
    public boolean hasUserVoted(String username) {
        return repo.findByUsername(username)
                .map(User::isHasVoted)
                .orElse(false);
    }
}
