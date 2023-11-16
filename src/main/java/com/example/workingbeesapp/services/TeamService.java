package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.ExtraServiceDto;
import com.example.workingbeesapp.dtos.TeamDto;
import com.example.workingbeesapp.dtos.WorkingSpaceDto;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.*;
import com.example.workingbeesapp.repositories.CompanyRepository;
import com.example.workingbeesapp.repositories.ExtraServiceRepository;
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
    private final ExtraServiceService extraServiceService;
    private final ExtraServiceRepository extraServiceRepository;

    public TeamService(TeamRepository teamRepository, CompanyRepository companyRepository, WorkingSpaceRepository workingSpaceRepository, WorkingSpaceService workingSpaceService, ExtraServiceService extraServiceService, ExtraServiceRepository extraServiceRepository) {
        this.teamRepository = teamRepository;
        this.companyRepository = companyRepository;
        this.workingSpaceRepository = workingSpaceRepository;
        this.workingSpaceService = workingSpaceService;
        this.extraServiceService = extraServiceService;
        this.extraServiceRepository = extraServiceRepository;
    }

    //--- get all teams ---//
    public List<TeamDto> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        List<TeamDto> teamDtoList = new ArrayList<>();
        for (Team team : teams) {
            TeamDto teamDto = transferTeamToTeamDto(team);
            teamDtoList.add(teamDto);
        }
        return teamDtoList;
    }

    //--- get all teams by company name ---//
    public List<TeamDto> getTeamsByCompanyName(String companyName) {
        List<Team> teamList = teamRepository.findAllByCompanyNameEqualsIgnoreCase(companyName);
        return transferTeamListToTeamDtoList(teamList);
    }

    //---get one team by id ---//
    public TeamDto getOneTeam(Long id) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isPresent()) {
            return transferTeamToTeamDto(optionalTeam.get());
        } else {
            throw new RecordNotFoundException("Item of type Team with id: " + id + " could not be found.");
        }
    }

    //--- create new team ---//
    public TeamDto createTeam(TeamDto teamDto) {
        Team newTeam = transferTeamDtoToTeam(teamDto);
        teamRepository.save(newTeam);
        return transferTeamToTeamDto(newTeam);
    }

    //--- update team ---//
    public TeamDto updateTeam(Long id, TeamDto teamDto) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isPresent()) {

            Team team = optionalTeam.get();

            Team updatedTeam = transferTeamDtoToTeam(teamDto);

            updatedTeam.setId(team.getId());

            teamRepository.save(updatedTeam);

            return transferTeamToTeamDto(updatedTeam);
        } else {
            throw new RecordNotFoundException("Item of type Team with id: " + id + " could not be found.");
        }
    }

    //--- delete team ---//
    public void deleteTeam(Long id) {
        if (teamRepository.existsById(id)) {
            Optional<Team> optionalTeam = teamRepository.findById(id);
            Team obsoleteTeam = optionalTeam.get();

            teamRepository.delete(obsoleteTeam);
        } else {
            throw new RecordNotFoundException("Item of type Team with id: " + id + " could not be found.");
        }
    }

    //--- assign company to team ---//
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

    //--- assign working space to team ---//
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

    //--- add working space to team ---//
    public void addWorkingSpace(WorkingSpaceDto workingSpaceDto, Team team) {
        if (workingSpaceDto != null) {
            WorkingSpace workingSpace = workingSpaceService.transferWorkingSpaceDtoToWorkingSpace(workingSpaceDto);
            if (team.getWorkingSpace() == null) {
                workingSpace.setTeam(team);
                team.setWorkingSpace(workingSpace);
                workingSpaceRepository.save(workingSpace);
                teamRepository.save(team);
            } else {
                System.out.println("Team already has a working space");
            }
        }
    }

    //--- add extra service to team ---//
    public void addExtraService(List<ExtraServiceDto> extraServices, Team team) {
        for (ExtraServiceDto extraService : extraServices) {
            if (!extraService.getExtraService().isEmpty()) {
                ExtraService extraService1 = extraServiceService.transferExtraServiceDtoToExtraService(extraService);
                extraService1.setTeam(team);
                extraServiceRepository.save(extraService1);
            }
        }
    }

    //--- transfer helper method team to team dto ---//
    public TeamDto transferTeamToTeamDto(Team team) {

        TeamDto teamDto = new TeamDto();

        teamDto.setId(team.getId());
        teamDto.setTeam(team.getTeam());
        teamDto.setCompanyName(team.getCompanyName());
        teamDto.setTeamSize(team.getTeamSize());
        if (team.getExtraServices() != null) {
            teamDto.setExtraServices(extraServiceService.transferExtraServiceListToExtraServiceListDto(team.getExtraServices()));
        }
        if (team.getWorkingSpace() != null) {
            teamDto.setWorkingSpace(workingSpaceService.transferWorkingSpaceToWorkingSpaceDto(team.getWorkingSpace()));
        }

        return teamDto;
    }

    //--- transfer helper method team dto to team ---//
    public Team transferTeamDtoToTeam(TeamDto teamDto) {

        Team team = new Team();

        team.setId(teamDto.getId());
        team.setTeam(teamDto.getTeam());
        team.setCompanyName(teamDto.getCompanyName());
        team.setTeamSize(teamDto.getTeamSize());
        return team;
    }

    // transfer methods for team list to team dto //
    // TODO : IF THIS ONE IS UNUSED - DELETE IT! :) //
    public List<Team> transferTeamDtoListToTeamList(List<TeamDto> teamDtoList) {
        List<Team> teams = new ArrayList<>();
        for (TeamDto teamsDto : teamDtoList) {
            teams.add(transferTeamDtoToTeam(teamsDto));
        }
        return teams;
    }

    //--- transfer helper method team list to team dto list ---//
    public List<TeamDto> transferTeamListToTeamDtoList(List<Team> teamList) {
        List<TeamDto> teamDtoList = new ArrayList<>();
        for (Team teams : teamList) {
            teamDtoList.add(transferTeamToTeamDto(teams));
        }
        return teamDtoList;
    }
}