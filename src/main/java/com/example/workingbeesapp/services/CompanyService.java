package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.CompanyDto;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.Company;;
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

    private final SubscriptionRepository subscriptionRepository; // for to one-to-one relation with subscription //

    private final TeamRepository teamRepository; // for ONE-TO-MANY relation with TEAM - COMPANY IS ONE! //

    public CompanyService(CompanyRepository companyRepository, SubscriptionRepository subscriptionRepository, TeamRepository teamRepository) {
        this.companyRepository = companyRepository;
        this.subscriptionRepository = subscriptionRepository;
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
        companyRepository.save(newCompany);
        return transferCompanyToCompanyDto(newCompany);
    }


    // FUNCTION TO UPDATE COMPANY //
    public CompanyDto updateCompany(Long id, CompanyDto companyDto) {
        if (companyRepository.findById(id).isPresent()) {

            Company company = companyRepository.findById(id).get();

            Company company1 = transferCompanyDtoToCompany(companyDto);

            company1.setId(company.getId());

            companyRepository.save(company1);

            return transferCompanyToCompanyDto(company1);
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


    // ******* TRANSFER HELPER METHODS HERE!!!  ******* //

    public CompanyDto transferCompanyToCompanyDto(Company company) {

        var companyDto = new CompanyDto();

        companyDto.setId(company.getId());
        companyDto.setCompanyName(company.getCompanyName());
        companyDto.setTeamName(company.getTeamName());
        companyDto.setCompanyDetails(company.getCompanyDetails());
        companyDto.setPaymentDetails(company.getPaymentDetails());

        return companyDto;
    }

    public Company transferCompanyDtoToCompany(CompanyDto dto) {

        Company company = new Company();

        company.setId(dto.getId());
        company.setCompanyName(dto.getCompanyName());
        company.setTeamName(dto.getTeamName());
        company.setCompanyDetails(dto.getCompanyDetails());
        company.setPaymentDetails(dto.getPaymentDetails());


        return company;
    }

    // TRANSFER COMPANY LIST DTO TO LIST //
    public List<Company> transferCompanyDtoListToCompanyList(List<CompanyDto> companiesDtos) {
        List<Company> companies = new ArrayList<>();
        for (CompanyDto teamsDto : companiesDtos) {
            companies.add(transferCompanyDtoToCompany(teamsDto));
        }
        return companies;
    }

    // --- assigning SUBSCRIPTION TO COMPANY --- //

    public void assignSubscriptionToCompany(Long companyId, Long subscriptionId) {
        var optionalCompany = companyRepository.findById(companyId);
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
}

