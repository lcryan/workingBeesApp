package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.SubscriptionDto;

import com.example.workingbeesapp.exceptions.RecordNotFoundException;
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
        List<SubscriptionDto> subscriptionDtoList = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            SubscriptionDto subscriptionDto = transferSubscriptionToSubscriptionDto(subscription);
            subscriptionDtoList.add(subscriptionDto);
        }
        return subscriptionDtoList;
    }

    // FUNCTION FOR GET ONE COMPANY //

    public SubscriptionDto getOneSubscription(Long id) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        if (optionalSubscription.isPresent()) {
            SubscriptionDto subscription = transferSubscriptionToSubscriptionDto(optionalSubscription.get());
            return subscription;
        } else {
            throw new RecordNotFoundException("Item of type Subscription with id: " + id + " could not be found.");
        }
    }

    // FUNCTION FOR CREATING ONE COMPANY //

    public SubscriptionDto createSubscription(SubscriptionDto subscriptionDto) {
        Subscription newSubscription = transferSubscriptionDtoToSubscription(subscriptionDto);
        subscriptionRepository.save(newSubscription);
        return transferSubscriptionToSubscriptionDto(newSubscription);
    }

    // FUNCTION TO UPDATE COMPANY //
    public SubscriptionDto updateSubscription(Long id, SubscriptionDto subscriptionDto) {
        if (subscriptionRepository.findById(id).isPresent()) {

            Subscription subscription = subscriptionRepository.findById(id).get();

            Subscription company1 = transferSubscriptionDtoToSubscription(subscriptionDto);

            company1.setId(subscription.getId());

            subscriptionRepository.save(company1);

            return transferSubscriptionToSubscriptionDto(company1);
        } else {
            throw new RecordNotFoundException("Item of type Subscription with id: " + id + " could not be found.");
        }
    }

    // FUNCTION TO DELETE COMPANY //
    public void deleteSubscription(Long id) {
        if (subscriptionRepository.existsById(id)) {
            Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
            Subscription obsoleteSubscription = optionalSubscription.get();

            subscriptionRepository.delete(obsoleteSubscription);
        } else {
            throw new RecordNotFoundException("Item of type Subscription with id: " + id + " could not be found.");
        }
    }


    // ******* TRANSFER HELPER METHODS HERE!!!  ******* //

    public SubscriptionDto transferSubscriptionToSubscriptionDto(Subscription subscription) {

        SubscriptionDto subscriptionDto = new SubscriptionDto();

        subscriptionDto.setId(subscription.getId());
        subscriptionDto.setWorkingSpaceType(subscription.getWorkingSpaceType());
        subscriptionDto.setDuration(subscription.getDuration());
        subscriptionDto.setStartDate(subscription.getStartDate());
        subscriptionDto.setEndDate(subscription.getEndDate());
        subscriptionDto.setPrice(subscription.getPrice());

        return subscriptionDto;
    }

    public Subscription transferSubscriptionDtoToSubscription(SubscriptionDto subscriptionDto) {

        Subscription subscription = new Subscription();

        subscription.setId(subscriptionDto.getId());
        subscription.setWorkingSpaceType(subscriptionDto.getWorkingSpaceType());
        subscription.setDuration(subscriptionDto.getDuration());
        subscription.setStartDate(subscriptionDto.getStartDate());
        subscription.setEndDate(subscriptionDto.getEndDate());
        subscription.setPrice(subscriptionDto.getPrice());

        return subscription;
    }
}

