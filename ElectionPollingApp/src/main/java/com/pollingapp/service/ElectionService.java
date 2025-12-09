package com.pollingapp.service;

import com.pollingapp.model.ElectionControl;

public interface ElectionService {
    ElectionControl getControl(); // returns single control (create default if missing)
    ElectionControl setVotingEnabled(boolean enabled);
}
