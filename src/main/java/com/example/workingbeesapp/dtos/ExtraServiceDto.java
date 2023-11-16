package com.example.workingbeesapp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

public class ExtraServiceDto {

    private Long id;

    @NotBlank(message = "Service name is mandatory")
    private String extraService;

    @NotBlank(message = "Company name is mandatory")
    private String companyName;

    @NotBlank(message = "Service type is mandatory")
    private String serviceType;

    @NotBlank(message = "Service price is mandatory")
    private int servicePrice;

    @NotBlank(message = "Service duration is mandatory")
    private String serviceDuration;
}
