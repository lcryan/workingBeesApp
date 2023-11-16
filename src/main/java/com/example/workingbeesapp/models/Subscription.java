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
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "subscription_name")
    private String subscription;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "total_amount")
    private double totalAmount;

    //--- relation with company ---//
    @OneToOne(mappedBy = "subscription", cascade = CascadeType.ALL)
    private Company company;

    //--- relation with working space ---//
    @OneToMany(mappedBy = "subscription")
    private List<WorkingSpace> workingSpaces;
}
