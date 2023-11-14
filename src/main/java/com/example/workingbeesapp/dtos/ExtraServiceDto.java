package com.example.workingbeesapp.dtos;

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

    private String extraService;

    private String serviceName;

    private Boolean isAvailable;

    private String companyName;

    private String serviceType;

    private int servicePrice;

    private String serviceDuration;
}
