package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.ExtraServiceDto;
import com.example.workingbeesapp.dtos.SubscriptionDto;
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

    // FUNCTION FOR GET LIST OF TEAMS //
    public List<TeamDto> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        List<TeamDto> teamDtoList = new ArrayList<>();
        for (Team team : teams) {
            TeamDto teamDto = transferTeamToTeamDto(team);
            teamDtoList.add(teamDto);
        }
        return teamDtoList;
    }

    // FUNCTION FOR GETTING TEAMS BY COMPANY NAME //

    public List<TeamDto> getTeamsByCompanyName(String companyName) {
        List<Team> teamList = teamRepository.findAllByCompanyNameEqualsIgnoreCase(companyName);
        return transferTeamListToTeamDtoList(teamList);
    }

    // FUNCTION FOR GETTING ONE TEAM //
    public TeamDto getOneTeam(Long id) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isPresent()) {
            TeamDto team = transferTeamToTeamDto(optionalTeam.get());
            return team;
        } else {
            throw new RecordNotFoundException("Item of type Team with id: " + id + " could not be found.");
        }
    }

    // FUNCTION FOR CREATING A TEAM //

    public TeamDto createTeam(TeamDto teamDto) {
        Team newTeam = transferTeamDtoToTeam(teamDto);
        teamRepository.save(newTeam);
        return transferTeamToTeamDto(newTeam);
    }

    // FUNCTION FOR UPDATING TEAM //
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

    // FUNCTION FOR DELETING A TEAM //
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


/*    public void addSubscription(SubscriptionDto subscriptionDto, Company company) {
        if (subscriptionDto != null) {
            Subscription subscription = subscriptionService.transferSubscriptionDtoToSubscription(subscriptionDto);
            if (company.getSubscription() == null) {
                subscription.setCompany(company);
                company.setSubscription(subscription);
                subscriptionRepository.save(subscription);
                companyRepository.save(company);
            } else {
                System.out.println("Company already has a subscription");
            }
        }
    }*/
    // method to add extra service to team - functional in postman //

    public void addExtraService(List<ExtraServiceDto> extraServices, Team team) {
        for (ExtraServiceDto extraService : extraServices) {
            if (!extraService.getExtraService().isEmpty()) {
                ExtraService extraService1 = extraServiceService.transferExtraServiceDtoToExtraService(extraService);
                extraService1.setTeam(team);
                extraServiceRepository.save(extraService1);
            }
        }
    }

    // ******* TRANSFER HELPER METHODS HERE!!!  ******* //
    public TeamDto transferTeamToTeamDto(Team team) {

        TeamDto teamDto = new TeamDto();

        teamDto.setId(team.getId());
        teamDto.setTeam(team.getTeam());
        teamDto.setTeamName(team.getTeamName());
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

    public Team transferTeamDtoToTeam(TeamDto teamDto) {

        Team team = new Team();

        team.setId(teamDto.getId());
        team.setTeam(teamDto.getTeam());
        team.setTeamName(teamDto.getTeamName());
        team.setCompanyName(teamDto.getCompanyName());
        team.setTeamSize(teamDto.getTeamSize());
        return team;
    }

// transfer methods for TeamList to TeamListDto //

    // TODO : IF THIS ONE IS UNUSED - DELETE IT! :) //
    public List<Team> transferTeamDtoListToTeamList(List<TeamDto> teamDtoList) {
        List<Team> teams = new ArrayList<>();
        for (TeamDto teamsDto : teamDtoList) {
            teams.add(transferTeamDtoToTeam(teamsDto));
        }
        return teams;
    }

    public List<TeamDto> transferTeamListToTeamDtoList(List<Team> teamList) {
        List<TeamDto> teamDtoList = new ArrayList<>();
        for (Team teams : teamList) {
            teamDtoList.add(transferTeamToTeamDto(teams));
        }
        return teamDtoList;
    }
}