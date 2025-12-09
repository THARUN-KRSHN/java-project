package com.election.entity;
import jakarta.persistence.*;

@Entity
public class ElectionMetadata {
    @Id private Long id = 1L; // Singleton: Always ID 1
    
    private boolean votingEnabled = false;
    private boolean resultsPublished = false;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isVotingEnabled() { return votingEnabled; }
    public void setVotingEnabled(boolean votingEnabled) { this.votingEnabled = votingEnabled; }
    public boolean isResultsPublished() { return resultsPublished; }
    public void setResultsPublished(boolean resultsPublished) { this.resultsPublished = resultsPublished; }
}