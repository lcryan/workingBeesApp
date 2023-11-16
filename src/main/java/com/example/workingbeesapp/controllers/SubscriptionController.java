package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.SubscriptionDto;
import com.example.workingbeesapp.repositories.SubscriptionRepository;
import com.example.workingbeesapp.services.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService, SubscriptionRepository subscriptionRepository) {
        this.subscriptionService = subscriptionService;
    }

    //--- get all subscriptions ---//
    @GetMapping("")
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions() {
        List<SubscriptionDto> subscriptionDtoList = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptionDtoList);
    }

    //--- get subscription ---//
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDto> getSubscription(@PathVariable("id") Long id) { // changed because of relation to COMPANY ONE TO ONE
        SubscriptionDto subscriptionDto = subscriptionService.getOneSubscription(id);
        return ResponseEntity.ok(subscriptionDto);
    }

    //--- create subscription ---//
    @PostMapping("")
    public ResponseEntity<Object> createNewSubscription(@Validated @RequestBody SubscriptionDto subscriptionDto, BindingResult bindingResult) {
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
            SubscriptionDto subscriptionDto1 = subscriptionService.createSubscription(subscriptionDto);
            return ResponseEntity.created(null).body(subscriptionDto1);
        }
    }

    //--- update subscription ---//
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDto> updateSubscription(@PathVariable Long id, @Validated @RequestBody SubscriptionDto newSubscription) {

        SubscriptionDto subscriptionDto1 = subscriptionService.updateSubscription(id, newSubscription);
        return ResponseEntity.ok().body(subscriptionDto1);
    }

    //--- delete subscription ---//
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubscription(@PathVariable Long id) {

        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}

