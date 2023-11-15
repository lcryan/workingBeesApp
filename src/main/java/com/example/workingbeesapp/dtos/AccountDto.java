package com.example.workingbeesapp.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter


public class AccountDto {

    private Long id;

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Company name cannot be empty")
    private String companyName;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "A role has to be assigned")
    private String[] roleList;
}
