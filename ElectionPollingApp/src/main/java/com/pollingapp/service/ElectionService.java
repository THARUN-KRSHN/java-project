package com.pollingapp.service;

import com.pollingapp.model.ElectionControl;

public interface ElectionService {

    ElectionControl getControl();

    ElectionControl setVotingEnabled(boolean enabled);
}
