package com.example.workingbeesapp.dtos;

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

    @NotEmpty(message = "Password cannot be empty")
    private String password;
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotEmpty(message = "A role has to be assigned")
    private String[] roleList;

}
