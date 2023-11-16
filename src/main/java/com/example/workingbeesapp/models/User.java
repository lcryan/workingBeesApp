package com.example.workingbeesapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Table(name = "users")

public class User {

    @Id
    private String username;
    private String password;

    //--- relation with account ---//
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    Account account;

    //--- relation with role ---//
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roleList = new ArrayList<>();
}
