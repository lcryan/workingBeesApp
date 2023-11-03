package com.example.workingbeesapp.controllers;


import com.example.workingbeesapp.dtos.TeamDto;
import com.example.workingbeesapp.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // GET LIST OF TEAMS BY COMPANY NAME / GET LIST OF TEAMS IF NO COMPANYNAME ADDED //
    @GetMapping("")
    public ResponseEntity<List<TeamDto>> getTeamsByCompanyName(@RequestParam(value = "companyName", required = false) Optional<String> companyName) {
        List<TeamDto> teamDtos;
        if (companyName.isEmpty()) {
            teamDtos = teamService.getAllTeams();
        } else {
            teamDtos = teamService.getTeamsByCompanyName(companyName.get());
        }
        return ResponseEntity.ok().body(teamDtos);
    }

    // GET ONE TEAM //
    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable Long id) {
        TeamDto teamDto = teamService.getOneTeam(id);
        return ResponseEntity.ok(teamDto);
    }


    // CREATE TEAM //
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

    // UPDATING TEAM //
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

    @PutMapping("/{id}/assignCompany/{companyId}")
    public ResponseEntity<Object> assignCompanyToTeam(@PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        teamService.assignsCompanyToTeam(id, companyId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}/assignWorkingSpace/{workingSpaceId}")
    public ResponseEntity<Object> assignWorkingSpaceToTeam(@PathVariable("id") Long id, @PathVariable("workingSpaceId") Long workingSpaceId) {
        teamService.assignWorkingSpaceToTeam(id, workingSpaceId);
        return ResponseEntity.noContent().build();
    }
}

