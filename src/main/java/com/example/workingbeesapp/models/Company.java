package com.example.workingbeesapp.models;

import jakarta.persistence.*;
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

@Entity
@Table(name = "companies")

public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "company_details")
    private String companyDetails;

    @Column(name = "payment_details")
    private String paymentDetails;

    // --- relation to SUBSCRIPTION --- CHECKED --- functional ---//

    @OneToOne
    @JoinColumn(name = "subscription_overview")
    private Subscription subscription;

    @OneToMany(mappedBy = "company")
    private List<Team> teams;


    public void addTeam(Team team) {
        this.teams.add(team);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;

        return Objects.equals(id, company.id) && Objects.equals(companyName, company.companyName) && Objects.equals(companyDetails, company.companyDetails) && Objects.equals(teamName, company.teamName) && Objects.equals(paymentDetails, company.paymentDetails) && Objects.equals(subscription, company.subscription) && Objects.equals(teams, company.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, companyDetails, teamName, paymentDetails, subscription, teams);
    }
}
