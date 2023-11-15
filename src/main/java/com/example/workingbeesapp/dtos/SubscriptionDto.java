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

public class SubscriptionDto {

    private Long id;

    private String companyName;

    @NotEmpty(message = "Total amount is required")
    private double totalAmount;

    private List<WorkingSpaceDto> workingSpaces;
}
