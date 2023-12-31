package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.AccountUserDto;
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

    //--- create a user with account ---//
    @PostMapping("")
    public ResponseEntity<UserDto> createUserWithAccount(@Valid @RequestBody AccountUserDto accountUserDto) {
        UserDto result = userService.createUserWithAccount(accountUserDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    //--- get all users ---//
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = userService.getAllUsers();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    //--- get user by username ---//
    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        UserDto userDto = userService.getUser(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //--- delete user ---//
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>("User with username: " + username + " deleted", HttpStatus.OK);
    }
}
