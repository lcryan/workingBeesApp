package com.example.workingbeesapp.dtos;

import jakarta.validation.constraints.NotEmpty;
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

    // company related fields //
    public Long id; // TODO: check if this is needed //
    @NotEmpty(message = "Company name cannot be empty")
    private String companyName;
    @NotEmpty(message = "Company details cannot be empty")
    private String companyDetails;
    @NotEmpty(message = "Payment details cannot be empty")
    private String paymentDetails;

    // this is for security purposes - user and role related //
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotEmpty(message = "A role has to be assigned")
    private String[] roleList;
    // end security purposes //

    // --- TEAMS of relation One-To-Many ---  COMPANY IS ONE - TEAM IS MANY //
    private List<TeamDto> teams;
    // --- SUBSCRIPTION of relation One-To-One ---//
    private SubscriptionDto subscription; // remember! Lombok needs a different approach with setting here !!! You are setting setSubscription in service ! It is the name not the actual type of var!!!

}

// TODO : check, if this is a viable option or do I have to make an extra account enity ? //