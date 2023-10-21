package com.example.workingbeesapp.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

public class SubscriptionDto {

    private Long id;

    private double price;

    private String duration;

    private int startDate;

    private int endDate;

    private String workingSpaceType;
}
