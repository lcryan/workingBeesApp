package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.CompanyDto;
import com.example.workingbeesapp.dtos.IdInputDto;
import com.example.workingbeesapp.services.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping("")
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> companyDtoList = companyService.getAllCompanies();
        return ResponseEntity.ok(companyDtoList);
    }

    // GET ONE COMPANY --- functional//
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable Long id) {
        CompanyDto companyDto = companyService.getOneCompany(id);
        return ResponseEntity.ok(companyDto);
    }


    // CREATE COMPANY --- functional//
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

    // UPDATING COMPANY --- functional //
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable Long id, @Validated @RequestBody CompanyDto newCompany) {
        CompanyDto companyDto1 = companyService.updateCompany(id, newCompany);
        return ResponseEntity.ok().body(companyDto1);
    }

    // DELETING COMPANY --- functional //

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);

        return ResponseEntity.noContent().build();
    }

    // --- ASSIGNING SUBSCRIPTION TO COMPANY ONE - TO - ONE RELATION --- //
    // TODO : make this one work - currently not working in postman - gives back 404 //
    @PutMapping("/{id}/subscription")
    public void assignSubscriptionToCompany(@PathVariable("id") Long id, @Validated @RequestBody IdInputDto input) {
        companyService.assignSubscriptionToCompany(id, input.id);
    }
}
