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

public class SubscriptionDto {

    private Long id;

    private String companyName;

    private double totalAmount;

    private List<WorkingSpaceDto> workingSpaces;
}
