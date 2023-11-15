package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.CompanyDto;
import com.example.workingbeesapp.dtos.ExtraServiceDto;
import com.example.workingbeesapp.dtos.TeamDto;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.Company;;
import com.example.workingbeesapp.models.ExtraService;
import com.example.workingbeesapp.models.Team;
import com.example.workingbeesapp.repositories.CompanyRepository;

import com.example.workingbeesapp.repositories.SubscriptionRepository;
import com.example.workingbeesapp.repositories.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionService subscriptionService;

    private final TeamService teamService;

    private final TeamRepository teamRepository;

    public CompanyService(CompanyRepository companyRepository, SubscriptionRepository subscriptionRepository, SubscriptionService subscriptionService, TeamRepository teamRepository, TeamService teamService) {
        this.companyRepository = companyRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionService = subscriptionService;

        this.teamService = teamService;
        this.teamRepository = teamRepository;
    }


    // FUNCTION FOR GET COMPANY LIST //
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyDto> companyDtoList = new ArrayList<>(); // watch out: in case you have a list, don't forget to use plural - here: televisions//
        for (Company company : companies) {
            CompanyDto companyDto = transferCompanyToCompanyDto(company);
            companyDtoList.add(companyDto);
        }
        return companyDtoList;
    }

    // FUNCTION FOR GET ONE COMPANY //
    public CompanyDto getOneCompany(Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            return transferCompanyToCompanyDto(optionalCompany.get());
        } else {
            throw new RecordNotFoundException("Item of type Company with id: " + id + " could not be found.");
        }
    }

    // FUNCTION FOR CREATING ONE COMPANY //

    public CompanyDto createCompany(CompanyDto companyDto) {
        Company newCompany = transferCompanyDtoToCompany(companyDto);
        Company savedCompany = companyRepository.save(newCompany);
        return transferCompanyToCompanyDto(savedCompany);
    }


    // FUNCTION TO UPDATE COMPANY //
    public CompanyDto updateCompany(Long id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);

        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();

            Company updatedCompany = transferCompanyDtoToCompany(companyDto);
            updatedCompany.setId(company.getId());

            companyRepository.save(updatedCompany);

            return transferCompanyToCompanyDto(updatedCompany);
        } else {
            throw new RecordNotFoundException("Item of type Company with id: " + id + " could not be found.");
        }
    }

    // FUNCTION TO DELETE COMPANY //
    public void deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            Optional<Company> optionalCompany = companyRepository.findById(id);
            Company obsoleteCompany = optionalCompany.get();

            companyRepository.delete(obsoleteCompany);
        } else {
            throw new RecordNotFoundException("Item of type Company with id: " + id + " could not be found.");
        }
    }
    // --- assigning subscription to company --- //

    public void assignSubscriptionToCompany(Long id, Long subscriptionId) { // changed to id here instead of companyId //
        var optionalCompany = companyRepository.findById(id);
        var optionalSubscription = subscriptionRepository.findById(subscriptionId);

        if (optionalCompany.isPresent() && optionalSubscription.isPresent()) {
            var company = optionalCompany.get();
            var subscription = optionalSubscription.get();

            company.setSubscription(subscription);
            companyRepository.save(company);
        } else {
            throw new RecordNotFoundException("Item with id " + subscriptionId + " could not be found.");
        }
    }

    public void addTeam(List<TeamDto> teams, Company company) {
        for (TeamDto teamDto : teams) {
            if (!teamDto.getTeam().isEmpty()) {
                Team team = teamService.transferTeamDtoToTeam(teamDto);
                team.setCompany(company);
                teamRepository.save(team);
            }
        }
    }

    // ******* transfer helper methods ******* //

    public CompanyDto transferCompanyToCompanyDto(Company company) {

        var companyDto = new CompanyDto();

        companyDto.setId(company.getId());
        companyDto.setCompanyName(company.getCompanyName());
        companyDto.setCompanyDetails(company.getCompanyDetails());
        companyDto.setPaymentDetails(company.getPaymentDetails());
        if (company.getSubscription() != null) {
            companyDto.setSubscription(subscriptionService.transferSubscriptionToSubscriptionDto(company.getSubscription())); // REMEMBER : lombok needs different approach here in the setter !!! //
        }
        if (company.getTeams() != null) {
            companyDto.setTeams(teamService.transferTeamListToTeamDtoList(company.getTeams()));

        }
        return companyDto;
    }

    public Company transferCompanyDtoToCompany(CompanyDto dto) {

        Company company = new Company();

        company.setId(dto.getId());
        company.setCompanyName(dto.getCompanyName());
        company.setCompanyDetails(dto.getCompanyDetails());
        company.setPaymentDetails(dto.getPaymentDetails());

        return company;
    }

    // --- TRANSFER COMPANY LIST DTO TO LIST ---
    public List<Company> transferCompanyDtoListToCompanyList(List<CompanyDto> companiesDtos) {
        List<Company> companies = new ArrayList<>();
        for (CompanyDto companyDto : companiesDtos) {
            companies.add(transferCompanyDtoToCompany(companyDto));
        }
        return companies;
    }
}