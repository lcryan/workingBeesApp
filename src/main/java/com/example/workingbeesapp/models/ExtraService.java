package com.example.workingbeesapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "extra_services")
public class ExtraService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "service_price")
    private String servicePrice;

    @Column(name = "service_duration")
    private String serviceDuration;
}
