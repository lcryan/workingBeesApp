package com.example.workingbeesapp.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "price")
    private double price;
    @Column(name = "duration")
    private String duration;

    @Column(name = "working_space_type")
    private String workingSpaceType;

    @OneToOne(mappedBy = "subscription")
    private Company company;
}
