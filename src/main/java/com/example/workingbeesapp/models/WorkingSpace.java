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
    private String workingSpace;

    @Column(name = "space_type")
    private String type;

    @Column(name = "space_capacity")
    private int capacity;

    @Column(name = "duration")
    private String duration;

    @Column(name = "price_per_room")
    private double rentalPrice;

    @Column(name = "companyName")
    private String companyName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_available")
    private boolean isAvailable;

    //---relation with team---//
    @OneToOne(mappedBy = "workingSpace")
    private Team team;

    //---relation with subscription---//
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    //---relation with file---//
    @OneToOne
    FileDocument file;
}
