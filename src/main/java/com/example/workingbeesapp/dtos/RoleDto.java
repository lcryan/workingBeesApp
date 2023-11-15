package com.example.workingbeesapp.dtos;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

public class RoleDto {

    @NotEmpty(message = "Role name cannot be empty")
    private String roleName;
}
