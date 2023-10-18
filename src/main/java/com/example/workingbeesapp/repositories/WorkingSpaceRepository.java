package com.example.workingbeesapp.repositories;

import com.example.workingbeesapp.models.WorkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingSpaceRepository extends JpaRepository<WorkingSpace, Long> {
}
