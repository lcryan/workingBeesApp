package com.example.workingbeesapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


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

    @Column(name = "company_details")
    private String companyDetails;

    @Column(name = "payment_details")
    private String paymentDetails;

    // Relations //
    @OneToOne
    @JoinColumn(name = "your_subscriptions")
    public Subscription subscription;

    @OneToMany(mappedBy = "company")
    private List<Team> teams;

    //1. TODO : addTeam still has to be added to controller! This has to be finalised //
    //2. TODO: also it should be possible to add a subscription to a company - for the hub admin//

    public void addTeam(Team team) {
        this.teams.add(team);
        team.setCompany(this);
    }
}

