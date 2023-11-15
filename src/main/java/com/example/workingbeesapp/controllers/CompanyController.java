package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.CompanyDto;
import com.example.workingbeesapp.dtos.SubscriptionDto;
import com.example.workingbeesapp.dtos.TeamDto;
import com.example.workingbeesapp.models.Company;
import com.example.workingbeesapp.repositories.CompanyRepository;
import com.example.workingbeesapp.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    public CompanyController(CompanyService companyService, CompanyRepository companyRepository) {
        this.companyService = companyService;
        this.companyRepository = companyRepository;
    }

    // GET COMPANY LIST - BY NAME IF APPLICABLE //
    @GetMapping("")
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> companyDtoList = companyService.getAllCompanies();
        return ResponseEntity.ok(companyDtoList);
    }

    // GET ONE COMPANY BY ID //
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable Long id) {
        CompanyDto companyDto = companyService.getOneCompany(id);
        return ResponseEntity.ok(companyDto);
    }


    // CREATE COMPANY //
    @PostMapping("")
    public ResponseEntity<Object> createNewCompany(@Validated @RequestBody CompanyDto companyDto, BindingResult bindingResult) {
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
            CompanyDto companyDto1 = companyService.createCompany(companyDto);
            return ResponseEntity.created(null).body(companyDto1);
        }
    }


    // UPDATE COMPANY --- functional //
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable Long id, @Validated @RequestBody CompanyDto newCompany) {
        CompanyDto companyDto1 = companyService.updateCompany(id, newCompany);
        return ResponseEntity.ok().body(companyDto1);
    }

    // DELETE COMPANY --- functional //

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);

        return ResponseEntity.noContent().build();
    }

    // ASSIGN SUBSCRIPTION TO COMPANY //
    @PutMapping("/{id}/{subscriptionId}")
    public ResponseEntity<Object> assignSubscriptionToCompany(@PathVariable("id") Long id, @PathVariable("subscriptionId") Long subscriptionId) {
        companyService.assignSubscriptionToCompany(id, subscriptionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{companyId}/addTeam")
    public ResponseEntity<Object> addTeamsToCompany(@PathVariable Long companyId, @RequestBody List<TeamDto> teamDtoList) {
        try {
            Optional<Company> optionalCompany = companyRepository.findById(companyId);
            if (optionalCompany.isPresent()) {
                Company company = optionalCompany.get();
                companyService.addTeam(teamDtoList, company);
                return ResponseEntity.ok("A Team has been successfully added to company");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding teams to company.");
        }
    }

    @PostMapping("/{companyId}/addSubscription")
    public ResponseEntity<Object> addSubscriptionToCompany(@PathVariable Long companyId, @RequestBody SubscriptionDto subscriptionDto) {
        try {
            Optional<Company> optionalCompany = companyRepository.findById(companyId);
            if (optionalCompany.isPresent()) {
                Company company = optionalCompany.get();
                companyService.addSubscription(subscriptionDto, company);
                return ResponseEntity.ok("A Subscription has been successfully added to company");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding subscription to company.");
        }
    }
}

