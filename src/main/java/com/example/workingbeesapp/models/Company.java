package com.example.workingbeesapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "company_details")
    private String companyDetails;

    @Column(name = "payment_details")
    private String paymentDetails;

    // --- relation to SUBSCRIPTION --- CHECKED --- functional ---//
    // TODO : I would like the company to be able to see the full overview of their subscription - is this even possible in the backend ? //
    @OneToOne
    @JoinColumn(name = "subscription_overview")
    private Subscription subscription;

    @OneToMany(mappedBy = "company")
    private List<Team> teams;

    @OneToMany(mappedBy = "company")
    private List<ExtraService> extraServices;
}
