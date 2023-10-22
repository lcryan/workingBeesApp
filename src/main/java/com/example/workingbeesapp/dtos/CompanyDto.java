package com.example.workingbeesapp.dtos;

import com.example.workingbeesapp.models.ExtraService;
import com.example.workingbeesapp.models.Subscription;
import com.example.workingbeesapp.models.Team;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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

    // --- TEAMS of relation One-To-Many --- include props JSON //

    @JsonIncludeProperties({"id", "team_name", "working_space", "team_size", "extra_service"})
    private List<Team> teams;

    // --- EXTRA-SERVICE of relation One-To-Many --- include props JSON//
    @JsonIncludeProperties({"id", "service_name", "service_type", "service_price", "service_duration"})
    private List<ExtraService> extraServices;

    // --- SUBSCRIPTION of relation One-To-One --- include props JSON //

    @JsonIncludeProperties({"id", "price", "duration", "start_date", "end_date", "working_space_type"})
    private Subscription subscription;

}
