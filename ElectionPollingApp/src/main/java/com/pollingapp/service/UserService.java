package com.pollingapp.service;

import com.pollingapp.model.User;
import com.pollingapp.model.Role;

import java.util.Optional;

public interface UserService {

    User registerUser(String username, String rawPassword, Role role);

    Optional<User> findByUsername(String username);

    void markUserVoted(String username);

    boolean hasUserVoted(String username);
}
