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
@Table
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "company")
    private String company;

    //TODO: 1. add relation to company class and make this a list or set //

    @Column(name = "working_space")
    private String workingSpace;

    //TODO : 2. add relation to workingSpace class here and add a list / set here //

    @Column(name = "room_size")
    private int roomSize;

    @Column(name = "extra_service")
    private String extraService;

    // TODO: 3. add extraService relation here and add a list / set here //
}
