package com.example.workingbeesapp.controllers;


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
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
        String result = userService.createUser(userDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}") //GET /users/{userId}
    // This method handles HTTP GET requests to the /users/{userId} endpoint, where {id} is a path variable representing the property ID
    public ResponseEntity<UserDto> getOneUser(@PathVariable String userId) {
        UserDto userDto = userService.getUser(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // to get all users in the database//
    @GetMapping // GET /users //

    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = userService.getAllUsers();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }
}

