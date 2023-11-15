package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.CompanyDto;

import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.Company;
import com.example.workingbeesapp.models.Subscription;

import com.example.workingbeesapp.repositories.CompanyRepository;
import com.example.workingbeesapp.repositories.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;
    @Mock
    SubscriptionRepository subscriptionRepository;

    @InjectMocks
    CompanyService companyService;


    @Test
    void getAllCompanies() {
        Company companyOne = new Company();
        companyOne.setId(1L); // id is one here - specific to testing //
        companyOne.setCompanyName("Monster Inc.");
        companyOne.setCompanyDetails("Sully Alley 222, 222787 Los Angeles, Disney World");
        companyOne.setPaymentDetails("Mike Wazowski Bank, account number: 1234567890");

        Company companyTwo = new Company();
        companyTwo.setId(2L); // id is two here - specific to testing //
        companyTwo.setCompanyName("Reznor Labs");
        companyTwo.setCompanyDetails("Trent Reznor Street 666, 666666 New York, Brooklyn");
        companyTwo.setPaymentDetails("Downward Spiral Bank, account number: 0987654321");

        List<Company> testCompanies = companyService.transferCompanyDtoListToCompanyList(companyService.getAllCompanies());
        testCompanies.add(companyOne);
        testCompanies.add(companyTwo);

        when(companyRepository.findAll()).thenReturn(testCompanies);

        List<CompanyDto> result = companyService.getAllCompanies();

        assertEquals(testCompanies.size(), result.size());
    }

    @Test
    void getOneCompany() {

        Long id = 1L;
        Company companyOne = new Company();
        companyOne.setId(1L);
        companyOne.setCompanyName("ToriAmos Coop.");
        companyOne.setCompanyDetails("Under The Pink Street 667, 898788 New York, Brooklyn");
        companyOne.setCompanyDetails("Amos Bank, account number: 1234567890");

        when(companyRepository.findById(id)).thenReturn(java.util.Optional.of(companyOne));
        CompanyDto result = companyService.getOneCompany(id);

        assertEquals(companyOne.getId(), result.getId());
        assertEquals(companyOne.getCompanyName(), result.getCompanyName());
        assertEquals(companyOne.getCompanyDetails(), result.getCompanyDetails());
        assertEquals(companyOne.getPaymentDetails(), result.getPaymentDetails());
    }

    @Test
    void getOneCompanyNotFound() {
        Long id = 1L;
        when(companyRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> companyService.getOneCompany(id));
    }

    @Test
    void createCompany() {
        CompanyDto newCompanyDto = new CompanyDto();

        newCompanyDto.setCompanyName("Up! Coop.");
        newCompanyDto.setCompanyDetails("Balloon Street 453, 896306 Washington, D.C.");
        newCompanyDto.setPaymentDetails("Up! Bank, account number: 826307839");

        Company company = new Company();
        company.setCompanyName(newCompanyDto.getCompanyName());
        company.setCompanyDetails(newCompanyDto.getCompanyDetails());
        company.setPaymentDetails(newCompanyDto.getPaymentDetails());

        when(companyRepository.save(Mockito.any(Company.class))).thenReturn(company);

        CompanyDto companyDto = companyService.createCompany(newCompanyDto);

        assertEquals("Up! Coop.", companyDto.getCompanyName());
        assertEquals("Balloon Street 453, 896306 Washington, D.C.", companyDto.getCompanyDetails());
        assertEquals("Up! Bank, account number: 826307839", companyDto.getPaymentDetails());

    }

    @Test
    void updateCompany() {

        Long id = 1L;
        CompanyDto companyDto = new CompanyDto();

        companyDto.setId(id);
        companyDto.setCompanyName("The Three Hallows Inc.");
        companyDto.setCompanyDetails("Halloweentown 666, 666666 New York, Brooklyn");
        companyDto.setPaymentDetails("Halloweentown Bank, account number: 0987654321");

        Company existingCompany = new Company();

        existingCompany.setId(id);
        existingCompany.setCompanyName("Up! Coop.");
        existingCompany.setCompanyDetails("Balloon Street 453, 896306 Washington, D.C.");
        existingCompany.setCompanyDetails("Up! Bank, account number: 826307839");

        when(companyRepository.findById(id)).thenReturn(Optional.of(existingCompany));

        CompanyDto updatedCompanyDto = companyService.updateCompany(id, companyDto);

        verify(companyRepository, times(1)).findById(id);
        verify(companyRepository, times(1)).save(any(Company.class)); // testing if the save method is called on the repository //

        // Assert //
        assertNotNull(updatedCompanyDto);
        assertEquals(id, updatedCompanyDto.getId());
        assertEquals(companyDto.getCompanyName(), updatedCompanyDto.getCompanyName());
        assertEquals(companyDto.getCompanyDetails(), updatedCompanyDto.getCompanyDetails());
        assertEquals(companyDto.getPaymentDetails(), updatedCompanyDto.getPaymentDetails());

    }

    @Test
    void getUpdatedCompanyNotFound() {

        Long id = 1L;
        when(companyRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> companyService.updateCompany(id, new CompanyDto()));
    }


    @Test
    void deleteCompany() {
        Long id = 1L;
        Company existingCompany = new Company();
        existingCompany.setId(id);
        existingCompany.setCompanyName("Up! Coop.");
        existingCompany.setCompanyDetails("Balloon Street 453, 896306 Washington, D.C.");
        existingCompany.setPaymentDetails("Up! Bank, account number: 826307839");

        when(companyRepository.existsById(id)).thenReturn(true);
        when(companyRepository.findById(id)).thenReturn(Optional.of(existingCompany));

        companyService.deleteCompany(id);

        Mockito.verify(companyRepository, times(1)).delete(existingCompany);
    }

    @Test
    void assignSubscriptionToCompany() {
        Long companyId = 1L;
        Long subscriptionId = 2L;

        Company existingCompany = new Company();
        existingCompany.setId(companyId);
        existingCompany.setCompanyName("Up! Coop.");
        existingCompany.setCompanyDetails("Balloon Street 453, 896306 Washington, D.C.");
        existingCompany.setPaymentDetails("Up! Bank, account number: 826307839");

        Subscription existingSubscription = new Subscription();
        existingSubscription.setId(subscriptionId);
        existingSubscription.setCompanyName("Up! Coop.");
        existingSubscription.setWorkingSpaceType("Tokyo Valley CEO meeting room");
        existingSubscription.setTotalAmount(1000);

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(existingCompany));
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(existingSubscription));

        companyService.assignSubscriptionToCompany(companyId, subscriptionId);

        Mockito.verify(companyRepository, Mockito.times(1)).save(existingCompany);

        assertEquals(existingSubscription, existingCompany.getSubscription());
    }


    @Test
    void transferCompanyDtoToCompany() {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);
        companyDto.setCompanyName("Up! Coop.");
        companyDto.setCompanyDetails("Balloon Street 453, 896306 Washington, D.C.");
        companyDto.setPaymentDetails("Up! Bank, account number: 826307839");

        Company company = companyService.transferCompanyDtoToCompany(companyDto);

        assertEquals(companyDto.getId(), company.getId());
        assertEquals(companyDto.getCompanyName(), company.getCompanyName());
        assertEquals(companyDto.getCompanyDetails(), company.getCompanyDetails());
        assertEquals(companyDto.getPaymentDetails(), company.getPaymentDetails());
    }

}






