package com.election.repository;
import com.election.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CandidateRepository extends JpaRepository<Candidate, Long> {}