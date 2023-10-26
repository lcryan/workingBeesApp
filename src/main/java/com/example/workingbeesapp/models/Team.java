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
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "team_name")
    private String teamName;

    // -- Team is the owner of the relation - nothing in the db -- // TODO : ask Mark about this nothing in the db ? //
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id") // not a must //
    private Company company;

    @Column(name = "working_space")
    //TODO : 2. add relation to workingSpace class here - ONE TO ONE //
    private String workingSpace;

    @Column(name = "team_size")
    private int teamSize;

    // TODO: 3. add extraService relation here and add a list - every team can have more than one service//
    @Column(name = "extra_service")
    private String extraService;
}
