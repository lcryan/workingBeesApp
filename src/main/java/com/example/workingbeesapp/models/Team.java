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
    private String teamName;

    @Column(name = "company_name")
    private String companyName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne
    @JoinColumn(name = "team_working_space")
    private WorkingSpace workingSpace;

    @Column(name = "team_size")
    private int teamSize;

    @OneToMany(mappedBy = "team")

    private List<ExtraService> extraServices;

    // here you want to have a method that adds an extraService to team //
    public void addExtraService(ExtraService extraService) {
        this.extraServices.add(extraService);
        extraService.setTeam(this);
    }

    // here I want to add a method call that adds a workingSpace to team //
}
