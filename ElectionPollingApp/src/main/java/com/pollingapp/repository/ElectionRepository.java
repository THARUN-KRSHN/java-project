package com.pollingapp.repository;

import com.pollingapp.model.ElectionControl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<ElectionControl, Long> {
    // No extra methods needed for now
}
