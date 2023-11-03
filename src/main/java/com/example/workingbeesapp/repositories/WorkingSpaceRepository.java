package com.example.workingbeesapp.repositories;

import com.example.workingbeesapp.models.WorkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkingSpaceRepository extends JpaRepository<WorkingSpace, Long> {
    List<WorkingSpace> findAllByCompanyNameEqualsIgnoreCase(String companyName);
}
