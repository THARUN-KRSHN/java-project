package com.pollingapp.service.impl;

import com.pollingapp.model.Candidate;
import com.pollingapp.repository.CandidateRepository;
import com.pollingapp.service.CandidateService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository repo;

    public CandidateServiceImpl(CandidateRepository repo) {
        this.repo = repo;
    }

    @Override
    public Candidate addCandidate(Candidate candidate) {
        return repo.save(candidate);
    }

    @Override
    public Candidate updateCandidate(Long id, Candidate update) {
        Candidate existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));

        existing.setName(update.getName());
        existing.setCategory(update.getCategory());
        existing.setSymbolUrl(update.getSymbolUrl());

        return repo.save(existing);
    }

    @Override
    public void deleteCandidate(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Candidate> listAll() {
        return repo.findAll();
    }

    @Override
    public List<Candidate> listByCategory(String category) {
        return repo.findByCategory(category);
    }

    @Override
    public Optional<Candidate> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Candidate incrementVote(Long id) {
        Candidate c = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));

        c.setVoteCount(c.getVoteCount() + 1);
        return repo.save(c);
    }
}
