package com.example.workingbeesapp.dtos;

import com.example.workingbeesapp.models.FileDocument;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Name of working space is required")
    private String workingSpace;

    @NotBlank(message = "Name of company is required")
    private String companyName;

    @NotBlank(message = "Type of working space is required")
    private String type;

    @NotBlank(message = "Capacity of working space is required")
    @Min(1)
    @Max(200)
    private int capacity;

    private String duration;

    @NotBlank(message = "Rental price of working space is required")
    private double rentalPrice;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isAvailable;

    private FileDocument file;
}
