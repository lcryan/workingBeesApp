package com.example.workingbeesapp.dtos;

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

    private String teamName;

    private String companyName;

    private WorkingSpaceDto workingSpace;

    private int teamSize;

    private List<ExtraServiceDto> extraServices;
}
