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

    private String password;
    private String username;
    private List<String> roleList;

    @OneToOne
    User user;
}
