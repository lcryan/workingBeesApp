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
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String email;

    // setting up relation with Company class ONE-TO-ONE //

    @OneToOne(mappedBy = "user")
    Company company; // it is the company that signs up into the system, not the user //
    //---  set up relation with Role class --- //

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roleList = new ArrayList<>();

}
