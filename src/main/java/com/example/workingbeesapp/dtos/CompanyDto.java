package com.example.workingbeesapp.dtos;

import com.example.workingbeesapp.models.Subscription;
import com.example.workingbeesapp.models.Team;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

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

    // --- TEAMS of relation One-To-Many --- include props JSON //

    @JsonIncludeProperties({"id", "team_name", "working_space", "team_size", "extra_service"})
    private List<Team> teams;

    // --- SUBSCRIPTION of relation One-To-One --- include props JSON //

    @JsonIncludeProperties({"id", "price", "duration", "start_date", "end_date", "working_space_type"})
    Subscription subscription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyDto companyDto = (CompanyDto) o;

        return Objects.equals(id, companyDto.id) && Objects.equals(companyName, companyDto.companyName) && Objects.equals(companyDetails, companyDto.companyDetails) && Objects.equals(teamName, companyDto.teamName) && Objects.equals(paymentDetails, companyDto.paymentDetails) && Objects.equals(subscription, companyDto.subscription) && Objects.equals(teams, companyDto.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, companyDetails, teamName, paymentDetails, subscription, teams);
    }
}
