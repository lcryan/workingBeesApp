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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "your_subscriptions")
    public Subscription subscription;

    @OneToMany(mappedBy = "company")
    private List<Team> teams;

    public void addTeam(Team team) {
        this.teams.add(team);
        team.setCompany(this);
    }

    // this is for ADMIN only - a subscription can be then added to a company - the USER cannot do this with his authorization rights //
    public void addSubscription(Subscription subscription) {
        this.subscription = subscription;
        subscription.setCompany(this);
    }
}

