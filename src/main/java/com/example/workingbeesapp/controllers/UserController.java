package com.example.workingbeesapp.controllers;


import com.example.workingbeesapp.dtos.CompanyDto;
import com.example.workingbeesapp.dtos.UserDto;
import com.example.workingbeesapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("") // POST /users
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CompanyDto companyDto) {
        UserDto result = userService.createUserWithCompany(companyDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping // GET /users //
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = userService.getAllUsers();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }
}

