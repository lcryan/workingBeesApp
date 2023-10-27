package com.example.workingbeesapp.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

public class SubscriptionDto {

    private Long id;

    private double price;

    private String duration;

    private LocalDate startDate;

    private LocalDate endDate;

    private String workingSpaceType; // TODO: relation with teams ? make this into a list - with different types of working spaces - Paris room - Tokyo room etc. one-To-Many//
}
