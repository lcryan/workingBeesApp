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

    @OneToOne(mappedBy = "workingSpace")
    private Team team;
}
