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
    private final WorkingSpaceService workingSpaceService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, WorkingSpaceService workingSpaceService) {
        this.subscriptionRepository = subscriptionRepository;
        this.workingSpaceService = workingSpaceService;

    }

    //--- get all subscriptions ---//
    public List<SubscriptionDto> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        List<SubscriptionDto> subscriptionDtoList = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            SubscriptionDto subscriptionDto = transferSubscriptionToSubscriptionDto(subscription);
            subscriptionDtoList.add(subscriptionDto);
        }
        return subscriptionDtoList;
    }

    //--- get one subscription ---//
    public SubscriptionDto getOneSubscription(Long id) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        if (optionalSubscription.isPresent()) {
            SubscriptionDto subscription = transferSubscriptionToSubscriptionDto(optionalSubscription.get());
            return subscription;
        } else {
            throw new RecordNotFoundException("Item of type Subscription with id: " + id + " could not be found.");
        }
    }

    //--- create subscription ---//
    public SubscriptionDto createSubscription(SubscriptionDto subscriptionDto) {
        Subscription newSubscription = transferSubscriptionDtoToSubscription(subscriptionDto);
        subscriptionRepository.save(newSubscription);
        return transferSubscriptionToSubscriptionDto(newSubscription);
    }

    //--- update subscription ---//
    public SubscriptionDto updateSubscription(Long id, SubscriptionDto subscriptionDto) {

        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        if (optionalSubscription.isPresent()) {

            Subscription subscription = optionalSubscription.get();
            Subscription updatedSubscription = transferSubscriptionDtoToSubscription(subscriptionDto);
            updatedSubscription.setId(subscription.getId());

            subscriptionRepository.save(updatedSubscription);

            return transferSubscriptionToSubscriptionDto(updatedSubscription);
        } else {
            throw new RecordNotFoundException("Item of type Subscription with id: " + id + " could not be found.");
        }
    }

    //--- delete subscription ---//
    public void deleteSubscription(Long id) {
        if (subscriptionRepository.existsById(id)) {
            Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
            Subscription obsoleteSubscription = optionalSubscription.get();

            subscriptionRepository.delete(obsoleteSubscription);
        } else {
            throw new RecordNotFoundException("Item of type Subscription with id: " + id + " could not be found.");
        }
    }

    //--- transfer helper method for subscription to subscriptionDto ---//
    public SubscriptionDto transferSubscriptionToSubscriptionDto(Subscription subscription) {

        SubscriptionDto subscriptionDto = new SubscriptionDto();

        subscriptionDto.setId(subscription.getId());
        subscriptionDto.setSubscription(subscription.getSubscription());
        subscriptionDto.setTotalAmount(subscription.getTotalAmount());
        subscriptionDto.setCompanyName(subscription.getCompanyName());

        if (subscription.getWorkingSpaces() != null) {
            subscriptionDto.setWorkingSpaces(workingSpaceService.transferWorkingSpaceListToWorkingSpaceDtoList(subscription.getWorkingSpaces()));
        }
        return subscriptionDto;
    }

    //--- transfer helper method for subscriptionDto to subscription ---//
    public Subscription transferSubscriptionDtoToSubscription(SubscriptionDto subscriptionDto) {

        Subscription subscription = new Subscription();

        subscription.setId(subscriptionDto.getId());
        subscription.setSubscription(subscriptionDto.getSubscription());
        subscription.setTotalAmount(subscriptionDto.getTotalAmount());
        subscription.setCompanyName(subscriptionDto.getCompanyName());

        return subscription;
    }
}