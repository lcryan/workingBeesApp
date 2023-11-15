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

public class TeamDto {

    private Long id;

    private String team;

    @NotEmpty(message = "Team name is required")
    private String teamName;

    private String companyName;

    private WorkingSpaceDto workingSpace;

    @NotEmpty(message = "Team size is required")
    private int teamSize;

    private List<ExtraServiceDto> extraServices;
}
