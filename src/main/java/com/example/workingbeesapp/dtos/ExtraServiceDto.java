package com.example.workingbeesapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

public class ExtraServiceDto {

    private Long id;

    private String serviceName;

    private String serviceType;

    private int servicePrice;

    private String serviceDuration;
}
