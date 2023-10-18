package com.example.workingbeesapp.services;


import com.example.workingbeesapp.dtos.CompanyDto;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.Company;
import com.example.workingbeesapp.repositories.CompanyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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
            CompanyDto company = transferCompanyToCompanyDto(optionalCompany.get());
            return company;
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

    private CompanyDto transferCompanyToCompanyDto(Company company) {

        CompanyDto companyDto = new CompanyDto();

        companyDto.setId(company.getId());
        companyDto.setCompanyName(company.getCompanyName());
        companyDto.setTeamName(company.getTeamName());
        companyDto.setCompanyDetails(company.getCompanyDetails());
        companyDto.setPaymentDetails(company.getPaymentDetails());

        return companyDto;
    }

    private Company transferCompanyDtoToCompany(CompanyDto companyDto) {

        Company company = new Company();

        company.setId(companyDto.getId());
        company.setCompanyName(companyDto.getCompanyName());
        company.setTeamName(companyDto.getTeamName());
        company.setCompanyDetails(companyDto.getCompanyDetails());
        company.setPaymentDetails(companyDto.getPaymentDetails());

        return company;
    }
}
