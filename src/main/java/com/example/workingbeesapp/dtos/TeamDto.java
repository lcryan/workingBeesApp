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

    private String company;

    private String workingSpace;

    private String roomSize;

    private String extraService;
}
