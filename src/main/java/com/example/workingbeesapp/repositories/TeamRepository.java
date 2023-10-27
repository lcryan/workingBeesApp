package com.example.workingbeesapp.repositories;

import com.example.workingbeesapp.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TeamRepository extends JpaRepository<Team, Long> {
}
