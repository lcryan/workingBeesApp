package com.example.workingbeesapp.dtos;

import com.example.workingbeesapp.models.Company;
import com.example.workingbeesapp.models.WorkingSpace;
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

    private CompanyDto company; // TODO: check other Dto classes for the right set up! HAS TO BE DTO!  - it is correct here !!! //

    private WorkingSpaceDto workingSpace;

    private int teamSize;

    private String extraService;

}
