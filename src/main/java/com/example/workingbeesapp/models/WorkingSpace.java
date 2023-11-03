package com.example.workingbeesapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Table(name = "working_spaces")
public class WorkingSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "space_name")
    private String name;

    @Column(name = "space_type")
    private String type;

    @Column(name = "space_capacity")
    private int capacity;

    @Column(name = "duration")
    private String duration;

    @Column(name = "price_per_room")
    private double rentalPrice;

    // TODO 2: workingSpace and Subscription might need a OneToMany relation //

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToOne(mappedBy = "workingSpace")
    private Team team;
}
