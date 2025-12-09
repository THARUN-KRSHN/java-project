package com.pollingapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "election_control")
public class ElectionControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // true = voting enabled, false = voting stopped
    @Column(nullable = false)
    private boolean votingEnabled = false;

    public ElectionControl() {}

    public ElectionControl(boolean votingEnabled) {
        this.votingEnabled = votingEnabled;
    }

    // ----------- Getters & Setters ------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isVotingEnabled() {
        return votingEnabled;
    }

    public void setVotingEnabled(boolean votingEnabled) {
        this.votingEnabled = votingEnabled;
    }
}
