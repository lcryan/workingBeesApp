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

/*    @Column(name = "company")
    private String company;*/

    // -- Team is the owner of the relation - no foreign key in the db -- //
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    //TODO : 2. add relation to workingSpace class here //
    @Column(name = "working_space")
    private String workingSpace;

    @Column(name = "team_size")
    private int teamSize;

    // TODO: 3. add extraService relation here and add a //
    @Column(name = "extra_service")
    private String extraService;


}
