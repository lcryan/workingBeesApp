package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.CompanyDto;
import com.example.workingbeesapp.dtos.SubscriptionDto;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.Company;
import com.example.workingbeesapp.models.Subscription;
import com.example.workingbeesapp.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }


    public List<SubscriptionDto> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        List<SubscriptionDto> subscriptionDtoList = new ArrayList<>(); // watch out: in case you have a list, don't forget to use plural - here: televisions//
        for (Subscription subscription : subscriptions) {
            SubscriptionDto subscriptionDto = transferSubscriptionToSubscriptionDto(subscription);
            subscriptionDtoList.add(subscriptionDto);
        }
        return subscriptionDtoList;
    }

    // FUNCTION FOR GET ONE COMPANY //
    public CompanyDto getOneCompany(Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            CompanyDto subscription = transferCompanyToCompanyDto(optionalCompany.get());
            return subscription;
        } else {
            throw new RecordNotFoundException("Item of type Company with id: " + id + " could not be found.");
        }
    }

    // FUNCTION FOR CREATING ONE COMPANY //

    public CompanyDto createCompany(CompanyDto subscriptionDto) {
        Company newCompany = transferCompanyDtoToCompany(subscriptionDto);
        companyRepository.save(newCompany);
        return transferCompanyToCompanyDto(newCompany);
    }

    // FUNCTION TO UPDATE COMPANY //
    public CompanyDto updateCompany(Long id, CompanyDto subscriptionDto) {
        if (companyRepository.findById(id).isPresent()) {

            Company subscription = companyRepository.findById(id).get();

            Company company1 = transferCompanyDtoToCompany(subscriptionDto);

            company1.setId(subscription.getId());

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

    private SubscriptionDto transferSubscriptionToSubscriptionDto(Subscription subscription) {

        SubscriptionDto subscriptionDto = new SubscriptionDto();

        subscriptionDto.setId(subscription.getId());
        subscriptionDto.setWorkingSpaceType(subscription.getWorkingSpaceType());
        subscriptionDto.setDuration(subscription.getDuration());
        subscriptionDto.setPrice(subscription.getPrice());


        return subscriptionDto;
    }

    private Subscription transferSubscriptionDtoToSubscription(SubscriptionDto subscriptionDto) {

        Subscription subscription = new Subscription();

        subscription.setId(subscriptionDto.getId());
        subscription.setWorkingSpaceType(subscriptionDto.getWorkingSpaceType());
        subscription.setDuration(subscriptionDto.getDuration());
        subscription.setPrice(subscriptionDto.getPrice());

        return subscription;
    }
}

