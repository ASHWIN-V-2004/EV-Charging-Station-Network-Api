package com.skillfirstlab.chargingstationservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "charging_station")
public class ChargingStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stationId;

    private String stationName;

    private String city;

    private String location;

    private String connectorType;

    private Double chargingSpeed;

    private Integer availableSlots;

    private String stationStatus;


    public ChargingStation() {
    }

}