package com.example.workingbeesapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor // TODO : take this into consideration for TECHNICAL DECISIONS : why didn't I use @Data //
@NoArgsConstructor

@Getter
@Setter

@Entity
@Table(name = "users")

public class User {

    @Id
    private String username;
    private String password;

    // RELATION WITH COMPANY ONE TO ONE //
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    Company company;

    // RELATION WITH ROLE MANY TO MANY //
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roleList = new ArrayList<>();

}
