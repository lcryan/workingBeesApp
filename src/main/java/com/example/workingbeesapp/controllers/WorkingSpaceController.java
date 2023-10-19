package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.WorkingSpaceDto;
import com.example.workingbeesapp.services.WorkingSpaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workingspaces")
public class WorkingSpaceController {
    private final WorkingSpaceService workingSpaceService;

    public WorkingSpaceController(WorkingSpaceService workingSpaceService) {
        this.workingSpaceService = workingSpaceService;
    }

    @GetMapping("")
    public ResponseEntity<List<WorkingSpaceDto>> getAllWorkingSpaces() {
        List<WorkingSpaceDto> workingSpaceDtoList = workingSpaceService.getAllWorkingSpaces();
        return ResponseEntity.ok(workingSpaceDtoList);
    }

    // GET ONE TEAM --- CHECKED//
    @GetMapping("/{id}")
    public ResponseEntity<WorkingSpaceDto> getWorkingSpace(@PathVariable Long id) {
        WorkingSpaceDto workingSpaceDto = workingSpaceService.getOneWorkingSpace(id);
        return ResponseEntity.ok(workingSpaceDto);
    }


    // CREATE TEAM --- CHECKED//
    @PostMapping("")
    public ResponseEntity<Object> createNewWorkingSpace(@Validated @RequestBody WorkingSpaceDto workingSpaceDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                sb.append(fe.getField());
                sb.append(" : ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            WorkingSpaceDto workingSpaceDto1 = workingSpaceService.createWorkingSpace(workingSpaceDto);
            return ResponseEntity.created(null).body(workingSpaceDto1);
        }
    }

    // UPDATING TEAM ---  CHECKED//
    @PutMapping("/{id}")
    public ResponseEntity<WorkingSpaceDto> updateWorkingSpace(@PathVariable Long id, @Validated @RequestBody WorkingSpaceDto newTeam) {
        WorkingSpaceDto workingSpaceDto1 = workingSpaceService.updateWorkingSpace(id, newTeam);
        return ResponseEntity.ok().body(workingSpaceDto1);
    }

    // DELETING TEAM ---  CHECKED//

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWorkingSpace(@PathVariable Long id) {
        workingSpaceService.deleteWorkingSpace(id);

        return ResponseEntity.noContent().build();
    }
}

