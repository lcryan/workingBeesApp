package com.example.workingbeesapp.repositories;

import com.example.workingbeesapp.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
