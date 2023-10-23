package com.example.workingbeesapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

public class TeamDto {

    private Long id;

    private String teamName;

    // --- relation COMPANY --- //
    private CompanyDto companyDto;
    // --- relation WORKING-SPACE --- //
    private WorkingSpaceDto workingSpaceDto;

    private int teamSize;

    private String extraService;

}
