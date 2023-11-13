package com.example.workingbeesapp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "accounts")

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) // make sure to add id to db //
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "companyName")
    private String companyName;

    private String password;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "role_list")
    private String roleList;

    @OneToOne
    User user;
}
