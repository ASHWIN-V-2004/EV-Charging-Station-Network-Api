package com.skillfirstlab.chargingstationservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChargingStationRequestDTO {

    // Getter and Setter for stationName
    @NotBlank(message = "Station name is required")
    private String stationName;

    // Getter and Setter for city
    @NotBlank(message = "City is required")
    private String city;

    // Getter and Setter for location
    @NotBlank(message = "Location is required")
    private String location;

    // Getter and Setter for connectorType
    @NotBlank(message = "Connector type is required")
    private String connectorType;

    // Getter and Setter for chargingSpeed
    @Positive(message = "Charging speed must be greater than zero")
    private Double chargingSpeed;

    // Getter and Setter for availableSlots

    @Min(value = 0,
            message = "Available slots cannot be negative.")
    private Integer availableSlots;

    // Getter and Setter for stationStatus
    @NotBlank(message = "Station status is required")
    private String stationStatus;


}