package com.example.workingbeesapp.dtos;

import com.example.workingbeesapp.models.Company;
import com.example.workingbeesapp.models.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

public class TeamDto {

    private Long id;

    private String teamName;

    // --- relation COMPANY --- //
    private Company company;

    private String workingSpace;

    private int teamSize;

    private String extraService;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamDto that = (TeamDto) o;
        return Objects.equals(id, that.id) && Objects.equals(teamName, that.teamName) && Objects.equals(company, that.company) && Objects.equals(workingSpace, that.workingSpace) && Objects.equals(teamSize, that.teamSize) && Objects.equals(extraService, that.extraService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teamName, company, workingSpace, teamSize, extraService);
    }
}
