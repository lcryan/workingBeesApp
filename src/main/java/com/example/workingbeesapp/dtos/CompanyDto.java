package com.example.workingbeesapp.dtos;

import com.example.workingbeesapp.models.Subscription;
import com.example.workingbeesapp.models.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

public class CompanyDto {

    private Long id;

    private String companyName;

    private String teamName;

    private String companyDetails;

    private String paymentDetails;

    // --- TEAMS of relation One-To-Many ---  COMPANY IS ONE - TEAM IS MANY //
    private List<Team> teams; // correct //
    // --- SUBSCRIPTION of relation One-To-One ---//
    private Subscription subscription;
}
