package com.pollingapp.service.impl;

import com.pollingapp.model.ElectionControl;
import com.pollingapp.repository.ElectionRepository;
import com.pollingapp.service.ElectionService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository repo;

    public ElectionServiceImpl(ElectionRepository repo) {
        this.repo = repo;
    }

    @Override
    public ElectionControl getControl() {
        // Only 1 row is needed → if missing, create new.
        return repo.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> repo.save(new ElectionControl(false)));
    }

    @Override
    public ElectionControl setVotingEnabled(boolean enabled) {
        ElectionControl ec = getControl();
        ec.setVotingEnabled(enabled);
        return repo.save(ec);
    }
}
