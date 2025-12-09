package com.pollingapp.service;

import com.pollingapp.model.Candidate;
import java.util.List;
import java.util.Optional;

public interface CandidateService {
    Candidate addCandidate(Candidate candidate);
    Candidate updateCandidate(Long id, Candidate update);
    void deleteCandidate(Long id);
    List<Candidate> listAll();
    List<Candidate> listByCategory(String category);
    Optional<Candidate> findById(Long id);
    Candidate incrementVote(Long id);
}
