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

    /*private CompanyDto company;*/ // TODO: check other Dto classes for the right set up! HAS TO BE DTO!  - it is now correct here !!! Don't forget to change the transfermethod that gets the company in the teamService! //

    private WorkingSpaceDto workingSpace;

    private int teamSize;

    private String extraService;

}
