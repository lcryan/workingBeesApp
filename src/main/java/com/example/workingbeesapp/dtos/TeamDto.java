package com.example.workingbeesapp.dtos;

import com.example.workingbeesapp.models.Company;
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

    private Company company;

    private String workingSpace;

    private int teamSize;

    private String extraService;
}
