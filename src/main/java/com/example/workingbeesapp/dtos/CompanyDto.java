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
    private List<Team> teams; // TODO : check, if this also has to be set to DTO - list! //
    // --- SUBSCRIPTION of relation One-To-One ---//
    private SubscriptionDto subscription; // NOW : this has been changed to subscription Dto // // remember! Lombok needs a different approach with setting here !!! You are setting setSubscription in service ! It is the name not the actual type of var!!!
}
