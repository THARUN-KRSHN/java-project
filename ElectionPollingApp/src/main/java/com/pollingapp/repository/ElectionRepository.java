package com.pollingapp.repository;

import com.pollingapp.model.ElectionControl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<ElectionControl, Long> {
    // To store only one row (id=1) you can use findById(1L) etc.
}
