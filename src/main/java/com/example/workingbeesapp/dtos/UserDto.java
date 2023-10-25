package com.example.workingbeesapp.dtos;

import com.example.workingbeesapp.models.Authority;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

public class UserDto {

    private String username;

    private String password;

    private boolean enabled = true;

    private String apikey;

    private String firstname;

    private String lastname;

    private String email;

    @JsonSerialize
    public Set<Authority> authoritySet;

    // equals // comparing two objects for equality - comparing two instances of the class, that should be equal- & hashcode - used to calculate a hashcode - important if objects are stored as a hash table as in HashSet etc.  //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(username, userDto.username) && Objects.equals(password, userDto.password) && Objects.equals(enabled, userDto.enabled) && Objects.equals(apikey, userDto.apikey) && Objects.equals(firstname, userDto.firstname) && Objects.equals(lastname, userDto.lastname) && Objects.equals(email, userDto.email) && Objects.equals(authoritySet, userDto.authoritySet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, enabled, apikey, firstname, lastname, email);
    }
}
