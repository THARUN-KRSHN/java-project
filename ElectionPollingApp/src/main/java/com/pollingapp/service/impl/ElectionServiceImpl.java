package com.pollingapp.service.impl;

import com.pollingapp.model.ElectionControl;
import com.pollingapp.repository.ElectionRepository;
import com.pollingapp.service.ElectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository repo;

    public ElectionServiceImpl(ElectionRepository repo) {
        this.repo = repo;
    }

    @Override
    public ElectionControl getControl() {
        Optional<ElectionControl> o = repo.findAll().stream().findFirst();
        if (o.isPresent()) return o.get();
        ElectionControl ec = new ElectionControl(false);
        return repo.save(ec);
    }

    @Override
    public ElectionControl setVotingEnabled(boolean enabled) {
        ElectionControl ec = getControl();
        ec.setVotingEnabled(enabled);
        return repo.save(ec);
    }
}
