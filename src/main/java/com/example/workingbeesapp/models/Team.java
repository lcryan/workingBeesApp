package com.example.workingbeesapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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

    //TODO : 2. add relation to workingSpace class here - ONE TO ONE //
    @Column(name = "working_space")
    private String workingSpace;

    @Column(name = "team_size")
    private int teamSize;

    // TODO: 3. add extraService relation here and add a list - every team can have more than one service//
    @Column(name = "extra_service")
    private String extraService;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team that = (Team) o;
        return Objects.equals(id, that.id) && Objects.equals(teamName, that.teamName) && Objects.equals(company, that.company) && Objects.equals(workingSpace, that.workingSpace) && Objects.equals(teamSize, that.teamSize) && Objects.equals(extraService, that.extraService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teamName, company, workingSpace, teamSize, extraService);
    }
}
