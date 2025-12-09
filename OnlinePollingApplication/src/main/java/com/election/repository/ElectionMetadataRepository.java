package com.election.repository;
import com.election.entity.ElectionMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ElectionMetadataRepository extends JpaRepository<ElectionMetadata, Long> {}