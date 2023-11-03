package com.example.workingbeesapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

public class WorkingSpaceDto {

    private Long id;

    private String name;

    private String companyName;

    private String type;

    private int capacity;

    private String duration;

    private double rentalPrice;

    private LocalDate startDate;

    private LocalDate endDate;

}
