package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.RoleDto;
import com.example.workingbeesapp.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    //--- get all roles ---//
    @GetMapping("")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> roleDtoList = roleService.getAllRoles();
        return new ResponseEntity<>(roleDtoList, HttpStatus.OK);
    }
}
