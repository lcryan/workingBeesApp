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

    @Column(name = "total_amount")
    private double totalAmount;

    // TODO : make a relation of workingSpaces here : oneToMany //
    @Column(name = "working_space_type")
    private String workingSpaceType;

    @OneToOne(mappedBy = "subscription")
    private Company company;

    @OneToMany(mappedBy = "subscription")
    private List<WorkingSpace> workingSpaces;

}
