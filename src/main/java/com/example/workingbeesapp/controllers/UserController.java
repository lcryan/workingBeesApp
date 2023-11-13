package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.AccountDto;
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
    public ResponseEntity<UserDto> createUserWithAccount(@Valid @RequestBody AccountDto accountDto) {
        UserDto result = userService.createUserWithAccount(accountDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping // GET /users //
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = userService.getAllUsers();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        UserDto userDto = userService.getUser(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>("User with username: " + username + " deleted", HttpStatus.OK);
    }
}

// TODO : 1. Create a new user with account - is still not 100 % correct - needs adjustment in acocunt and user model & dto //