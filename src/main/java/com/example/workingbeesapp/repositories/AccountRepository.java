package com.example.workingbeesapp.repositories;

import com.example.workingbeesapp.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
