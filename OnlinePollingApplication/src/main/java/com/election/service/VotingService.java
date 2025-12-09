package com.election.service;
import com.election.entity.Candidate;
import com.election.entity.User;
import com.election.repository.CandidateRepository;
import com.election.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VotingService {
    @Autowired private CandidateRepository candidateRepo;
    @Autowired private UserRepository userRepo;

    public List<Candidate> getAllCandidates() { return candidateRepo.findAll(); }

    public void addCandidate(String name, String party) {
        Candidate c = new Candidate();
        c.setName(name);
        c.setParty(party);
        candidateRepo.save(c);
    }

    public long getTotalVotes() {
        return candidateRepo.findAll().stream().mapToLong(Candidate::getVoteCount).sum();
    }

    @Transactional
    public void castVote(Long candidateId, String username) {
        User voter = userRepo.findByUsername(username).orElseThrow();
        if (voter.isHasVoted()) throw new RuntimeException("Already voted");

        Candidate c = candidateRepo.findById(candidateId).orElseThrow();
        c.setVoteCount(c.getVoteCount() + 1);
        voter.setHasVoted(true);

        candidateRepo.save(c);
        userRepo.save(voter);
    }
}