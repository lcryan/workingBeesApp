package com.example.workingbeesapp.repositories;

import com.example.workingbeesapp.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
