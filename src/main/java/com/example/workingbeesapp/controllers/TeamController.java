package com.example.workingbeesapp.controllers;


import com.example.workingbeesapp.dtos.IdInputDto;
import com.example.workingbeesapp.dtos.TeamDto;
import com.example.workingbeesapp.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // GETTING TEAM LIST  --- CHECKED //

    @GetMapping("")
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        List<TeamDto> teamDtoList = teamService.getAllTeams();
        return ResponseEntity.ok(teamDtoList);
    }

    // GET ONE TEAM --- CHECKED//
    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable Long id) {
        TeamDto teamDto = teamService.getOneTeam(id);
        return ResponseEntity.ok(teamDto);
    }


    // CREATE TEAM --- CHECKED//
    @PostMapping("")
    public ResponseEntity<Object> createNewTeam(@Validated @RequestBody TeamDto teamDto, BindingResult bindingResult) {
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
            TeamDto teamDto1 = teamService.createTeam(teamDto);
            return ResponseEntity.created(null).body(teamDto1);
        }
    }

    // UPDATING TEAM ---  CHECKED//
    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id, @Validated @RequestBody TeamDto newTeam) {
        TeamDto teamDto1 = teamService.updateTeam(id, newTeam);
        return ResponseEntity.ok().body(teamDto1);
    }

    // DELETING TEAM ---  CHECKED//

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/{companyId}")
    public ResponseEntity<Object> assignCompanyToTeam(@PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        teamService.assignsCompanyToTeam(id, companyId);
        return ResponseEntity.noContent().build();
    }

    // TODO : this has to be corrected - step for 3.11.2023 @PutMapping is not coming out correctly in postman //
    @PutMapping("/{id}/{workingSpaceId}")
    public void assignWorkingSpaceToTeam(@PathVariable("id") Long id, @PathVariable("workingSpaceId") Long workingSpaceId) {
        teamService.assignWorkingSpaceToTeam(id, workingSpaceId);

        // had ambigious request in postman - ammended the request body to workingSpaceId instead of InputId //

        //TODO : to be completed! //
    }
}

