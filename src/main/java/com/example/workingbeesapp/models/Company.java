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

    // --- relation to SUBSCRIPTION --- //
    @OneToOne
    @JoinColumn(name = "subscription_overview")
    private Subscription subscription;

    @OneToMany(mappedBy = "company")
    /* @JsonIgnore*/ // TODO : check out, if you want to use  @JSON ignore here //
    private List<Team> teams;
}
