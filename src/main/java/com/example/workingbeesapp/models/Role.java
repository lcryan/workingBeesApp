package com.example.workingbeesapp.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

@Entity
@Table(name = "roles")
public class Role {

    @Id
    private String roleName;

    // relation user and role // many to many // user can have many roles // role can have many users//
    @ManyToMany(mappedBy = "roleList")
    private List<User> userList;

}
