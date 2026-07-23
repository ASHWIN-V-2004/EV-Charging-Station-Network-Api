package com.skillfirstlab.chargingstationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargingStationResponseDTO {

    private Long stationId;
    private String stationName;
    private String city;
    private String location;
    private String connectorType;
    private Double chargingSpeed;
    private Integer availableSlots;
    private String stationStatus;
    private String message;

    public ChargingStationResponseDTO() {
    }
}