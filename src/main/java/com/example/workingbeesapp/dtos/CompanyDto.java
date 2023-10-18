package com.example.workingbeesapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

public class CompanyDto {

    private Long id;

    private String companyName;

    private String teamName;

    private String companyDetails;

    private String paymentDetails;
}
