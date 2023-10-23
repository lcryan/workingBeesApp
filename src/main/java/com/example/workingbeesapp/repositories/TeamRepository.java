package com.example.workingbeesapp.repositories;

import com.example.workingbeesapp.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findTeamsByCompany_CompanyName(String companyName);
}
