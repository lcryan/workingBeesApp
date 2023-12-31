package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.ExtraServiceDto;
import com.example.workingbeesapp.dtos.TeamDto;
import com.example.workingbeesapp.dtos.WorkingSpaceDto;
import com.example.workingbeesapp.models.Team;
import com.example.workingbeesapp.repositories.TeamRepository;
import com.example.workingbeesapp.services.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamRepository teamRepository;
    private final TeamService teamService;

    public TeamController(TeamRepository teamRepository, TeamService teamService) {
        this.teamRepository = teamRepository;
        this.teamService = teamService;
    }

    //--- get list of teams sorted alphabetically by company name, if applicable ---//
    @GetMapping("")
    public ResponseEntity<List<TeamDto>> getTeamsByCompanyName(@RequestParam(value = "companyName", required = false) Optional<String> companyName) {
        List<TeamDto> teamDtos;
        if (companyName.isEmpty()) {
            teamDtos = teamService.getAllTeams();
        } else {
            teamDtos = teamService.getTeamsByCompanyName(companyName.get());
        }
        //--- sorting teams alphabetically here ---//
        teamDtos.sort(Comparator.comparing(TeamDto::getCompanyName));
        return ResponseEntity.ok().body(teamDtos);
    }

    //--- get team ---//
    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable Long id) {
        TeamDto teamDto = teamService.getOneTeam(id);
        return ResponseEntity.ok(teamDto);
    }

    //--- create team ---//
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

    //--- update team ---//
    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id, @Validated @RequestBody TeamDto newTeam) {
        TeamDto teamDto1 = teamService.updateTeam(id, newTeam);
        return ResponseEntity.ok().body(teamDto1);
    }

    //--- delete team ---//
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    //--- add extra service to team ---//
    @PostMapping("/{teamId}/addExtraService")
    public ResponseEntity<Object> addExtraServiceToTeam(@PathVariable Long teamId, @RequestBody List<ExtraServiceDto> extraServices) {
        try {
            Optional<Team> optionalTeam = teamRepository.findById(teamId);
            if (optionalTeam.isPresent()) {
                Team team = optionalTeam.get();
                teamService.addExtraService(extraServices, team);
                return ResponseEntity.ok("Extra services added successfully to the team.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding extra services to the team.");
        }
    }

    //--- add working space to team ---//
    @PostMapping("/{teamId}/addWorkingSpace")
    public ResponseEntity<Object> addWorkingSpaceToTeam(@PathVariable Long teamId, @RequestBody WorkingSpaceDto workingSpaceDto) {
        try {
            Optional<Team> optionalTeam = teamRepository.findById(teamId);
            if (optionalTeam.isPresent()) {
                Team team = optionalTeam.get();
                teamService.addWorkingSpace(workingSpaceDto, team);
                return ResponseEntity.ok("Working Space has been added successfully to team.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding extra working space to team.");
        }
    }

    //--- assign company to team ---//
    @PutMapping("/{id}/assignCompany/{companyId}")
    public ResponseEntity<Object> assignCompanyToTeam(@PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        teamService.assignsCompanyToTeam(id, companyId);
        return ResponseEntity.noContent().build();
    }

    //--- assign working space to team ---//
    @PutMapping("/{id}/assignWorkingSpace/{workingSpaceId}")
    public ResponseEntity<Object> assignWorkingSpaceToTeam(@PathVariable("id") Long id, @PathVariable("workingSpaceId") Long workingSpaceId) {
        teamService.assignWorkingSpaceToTeam(id, workingSpaceId);
        return ResponseEntity.noContent().build();
    }
}
