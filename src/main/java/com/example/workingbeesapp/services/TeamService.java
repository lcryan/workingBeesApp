package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.TeamDto;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.Team;
import com.example.workingbeesapp.repositories.CompanyRepository;
import com.example.workingbeesapp.repositories.TeamRepository;
import com.example.workingbeesapp.repositories.WorkingSpaceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    private final CompanyRepository companyRepository;

    private final WorkingSpaceRepository workingSpaceRepository;

    private final WorkingSpaceService workingSpaceService;


    public TeamService(TeamRepository teamRepository, CompanyRepository companyRepository, WorkingSpaceRepository workingSpaceRepository, WorkingSpaceService workingSpaceService) {
        this.teamRepository = teamRepository;

        this.companyRepository = companyRepository;
        this.workingSpaceRepository = workingSpaceRepository;
        this.workingSpaceService = workingSpaceService;
    }

    public List<TeamDto> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        List<TeamDto> teamDtoList = new ArrayList<>();
        for (Team team : teams) {
            TeamDto teamDto = transferTeamToTeamDto(team);
            teamDtoList.add(teamDto);
        }
        return teamDtoList;
    }


    public TeamDto getOneTeam(Long id) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isPresent()) {
            TeamDto team = transferTeamToTeamDto(optionalTeam.get());
            return team;
        } else {
            throw new RecordNotFoundException("Item of type Team with id: " + id + " could not be found.");
        }
    }

    // FUNCTION FOR CREATING ONE TEAM //

    public TeamDto createTeam(TeamDto teamDto) {
        Team newTeam = transferTeamDtoToTeam(teamDto);
        teamRepository.save(newTeam);
        return transferTeamToTeamDto(newTeam);
    }

    // FUNCTION TO UPDATE TEAM //
    public TeamDto updateTeam(Long id, TeamDto teamDto) {
        if (teamRepository.findById(id).isPresent()) {

            Team team = teamRepository.findById(id).get();

            Team company1 = transferTeamDtoToTeam(teamDto);

            company1.setId(team.getId());

            teamRepository.save(company1);

            return transferTeamToTeamDto(company1);
        } else {
            throw new RecordNotFoundException("Item of type Team with id: " + id + " could not be found.");
        }
    }

    // FUNCTION TO DELETE TEAM //
    public void deleteTeam(Long id) {
        if (teamRepository.existsById(id)) {
            Optional<Team> optionalTeam = teamRepository.findById(id);
            Team obsoleteTeam = optionalTeam.get();

            teamRepository.delete(obsoleteTeam);
        } else {
            throw new RecordNotFoundException("Item of type Team with id: " + id + " could not be found.");
        }
    }


    // --- assign TEAM(S) TO COMPANY -- MANY-TO-ONE-RELATION --- THIS IS THE OWNER OF THE RELATION --- //

    public void assignsCompanyToTeam(Long id, Long companyId) {
        var optionalTeam = teamRepository.findById(id);
        var optionalCompany = companyRepository.findById(companyId);

        if (optionalTeam.isPresent() && optionalCompany.isPresent()) {
            var team = optionalTeam.get();
            var company = optionalCompany.get();

            team.setCompany(company);
            teamRepository.save(team);
        } else {
            throw new RecordNotFoundException("Item not found.");
        }
    }


    // ******* TRANSFER HELPER METHODS HERE!!!  ******* //
    public TeamDto transferTeamToTeamDto(Team team) {

        TeamDto teamDto = new TeamDto();

        teamDto.setId(team.getId());
        teamDto.setTeamName(team.getTeamName());
        teamDto.setTeamSize(team.getTeamSize());
        teamDto.setExtraService(team.getExtraService());

        if (team.getWorkingSpace() != null) {
            teamDto.setWorkingSpace(workingSpaceService.transferWorkingSpaceToWorkingSpaceDto(team.getWorkingSpace()));
        } // getting working space for team //


        teamDto.setCompany(team.getCompany());

        return teamDto;
    }

    public Team transferTeamDtoToTeam(TeamDto teamDto) {

        Team team = new Team();

        team.setId(teamDto.getId());
        team.setTeamName(teamDto.getTeamName());

        team.setTeamSize(teamDto.getTeamSize());
        team.setExtraService(teamDto.getExtraService());

        return team;
    }

    // ASSIGNING WORKING SPACE TO TEAM //

    public void assignWorkingSpaceToTeam(Long id, Long workingSpaceId) {
        var optionalTeam = teamRepository.findById(id);
        var optionalWorkingSpace = workingSpaceRepository.findById(workingSpaceId);

        if (optionalTeam.isPresent() && optionalWorkingSpace.isPresent()) {

            var team = optionalTeam.get();
            var workingSpace = optionalWorkingSpace.get();

            team.setWorkingSpace(workingSpace);
            teamRepository.save(team);
        } else {
            throw new RecordNotFoundException("Item with id " + workingSpaceId + " couldn't be found.");
        }
    }
}