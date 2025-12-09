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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(String username, String rawPassword, Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        String hashed = passwordEncoder.encode(rawPassword);
        User user = new User(username, hashed, role);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public void markUserVoted(String username) {
        userRepository.findByUsername(username).ifPresent(u -> {
            u.setHasVoted(true);
            userRepository.save(u);
        });
    }

    @Override
    public boolean hasUserVoted(String username){
        return userRepository.findByUsername(username).map(User::isHasVoted).orElse(false);
    }
}
