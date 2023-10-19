package com.example.workingbeesapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

public class WorkingSpaceDto {

    private Long id;

    private String name;

    private String type;

    private String capacity;

    //TODO : don't forget to lay relation to team class ! //
}
