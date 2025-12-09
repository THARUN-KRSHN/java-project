package com.election.service;
import com.election.entity.ElectionMetadata;
import com.election.repository.ElectionMetadataRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemService {
    @Autowired private ElectionMetadataRepository repo;

    @PostConstruct
    public void init() {
        // Create the singleton metadata row if it doesn't exist
        if (repo.count() == 0) repo.save(new ElectionMetadata());
    }

    public ElectionMetadata getMetadata() {
        return repo.findById(1L).orElse(new ElectionMetadata());
    }

    public void toggleVoting(boolean enable) {
        ElectionMetadata meta = getMetadata();
        meta.setVotingEnabled(enable);
        repo.save(meta);
    }

    public void publishResults(boolean publish) {
        ElectionMetadata meta = getMetadata();
        meta.setResultsPublished(publish);
        repo.save(meta);
    }
}