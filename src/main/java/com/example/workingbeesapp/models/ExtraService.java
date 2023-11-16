package com.example.workingbeesapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Table(name = "extra_services")
public class ExtraService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "extra_service_name")
    private String extraService;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "service_price")
    private int servicePrice;

    @Column(name = "service_duration")
    private String serviceDuration;

    //--- relation with team ---//
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;
}
