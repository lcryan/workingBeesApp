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
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "team_name")
    private String team;

    @Column(name = "team_size")
    private int teamSize;

    @Column(name = "company_name")
    private String companyName;

    //--- relation with company ---//
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    //--- relation with working space ---//
    @OneToOne
    @JoinColumn(name = "team_working_space")
    private WorkingSpace workingSpace;

    //--- relation with extra services ---//
    @OneToMany(mappedBy = "team")
    private List<ExtraService> extraServices;
}
