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

    private String teamName; // TODO : This has to become a list - Many to One - one to many relation //

    private String companyDetails;

    private String paymentDetails;
}
