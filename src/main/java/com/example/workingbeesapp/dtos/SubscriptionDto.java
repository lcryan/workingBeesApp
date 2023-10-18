package com.example.workingbeesapp.dtos;

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

    private String workingSpaceType;
}
