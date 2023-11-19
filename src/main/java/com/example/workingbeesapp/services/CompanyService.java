package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.CompanyDto;
import com.example.workingbeesapp.dtos.SubscriptionDto;
import com.example.workingbeesapp.dtos.TeamDto;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.Company;;
import com.example.workingbeesapp.models.Subscription;
import com.example.workingbeesapp.models.Team;
import com.example.workingbeesapp.repositories.CompanyRepository;
import com.example.workingbeesapp.repositories.SubscriptionRepository;
import com.example.workingbeesapp.repositories.TeamRepository;
import org.springframework.stereotype.Service;

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

    //--- get all companies ---//
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyDto> companyDtoList = new ArrayList<>(); // watch out: in case you have a list, don't forget to use plural - here: televisions//
        for (Company company : companies) {
            CompanyDto companyDto = transferCompanyToCompanyDto(company);
            companyDtoList.add(companyDto);
        }
        return companyDtoList;
    }

    //--- get company ---//
    public CompanyDto getOneCompany(Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            return transferCompanyToCompanyDto(company);
        } else {
            throw new RecordNotFoundException("Item of type Company with id: " + id + " could not be found.");
        }
    }

    //--- create company ---//
    public CompanyDto createCompany(CompanyDto companyDto) {
        Company newCompany = transferCompanyDtoToCompany(companyDto);
        Company savedCompany = companyRepository.save(newCompany);
        return transferCompanyToCompanyDto(savedCompany);
    }

    //--- update company ---//
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

    //--- delete company ---//
    public void deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            Optional<Company> optionalCompany = companyRepository.findById(id);
            Company obsoleteCompany = optionalCompany.get();

            companyRepository.delete(obsoleteCompany);
        } else {
            throw new RecordNotFoundException("Item of type Company with id: " + id + " could not be found.");
        }
    }

    // --- assign subscription to company --- //
    public void assignSubscriptionToCompany(Long companyId, Long subscriptionId) { // changed to id here instead of companyId //
        var optionalCompany = companyRepository.findById(companyId);
        var optionalSubscription = subscriptionRepository.findById(subscriptionId);

        if (optionalCompany.isPresent() && optionalSubscription.isPresent()) {
            var company = optionalCompany.get();
            var subscription = optionalSubscription.get();

            company.setSubscription(subscription);
            subscription.setCompany(company);
            companyRepository.save(company);
            subscriptionRepository.save(subscription);
        } else {
            throw new RecordNotFoundException("Item with id " + subscriptionId + " could not be found.");
        }
    }

    //--- add team to company ---//
    public void addTeam(List<TeamDto> teams, Company company) {
        for (TeamDto teamDto : teams) {
            if (!teamDto.getTeam().isEmpty()) {
                Team team = teamService.transferTeamDtoToTeam(teamDto);
                team.setCompany(company);
                teamRepository.save(team);
            }
        }
    }

    //--- add subscription to company ---//
    public void addSubscription(SubscriptionDto subscriptionDto, Company company) {
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
    }

    //--- helper method for transfer company to company dto ---//

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

    //--- transfer helper method for company dto to company ---//
    public Company transferCompanyDtoToCompany(CompanyDto dto) {

        Company company = new Company();

        company.setId(dto.getId());
        company.setCompanyName(dto.getCompanyName());
        company.setCompanyDetails(dto.getCompanyDetails());
        company.setPaymentDetails(dto.getPaymentDetails());

        return company;
    }

    // --- transfer helper method for company dto list to company list ---//
    public List<Company> transferCompanyDtoListToCompanyList(List<CompanyDto> companiesDtos) {
        List<Company> companies = new ArrayList<>();
        for (CompanyDto companyDto : companiesDtos) {
            companies.add(transferCompanyDtoToCompany(companyDto));
        }
        return companies;
    }
}