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

    // --- relation COMPANY MANY TO ONE (TEAM MANY - COMPANY ONE--- //
    private Company company; // correct //
    // --- relation WORKING-SPACE --- ONE TO ONE//
    private WorkingSpace workingSpace;

    private int teamSize;

    private String extraService;

}
