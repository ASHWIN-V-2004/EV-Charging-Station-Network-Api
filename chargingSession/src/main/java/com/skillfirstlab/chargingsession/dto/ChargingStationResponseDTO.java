package com.skillfirstlab.chargingsession.dto;

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

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public Double getChargingSpeed() {
        return chargingSpeed;
    }

    public void setChargingSpeed(Double chargingSpeed) {
        this.chargingSpeed = chargingSpeed;
    }

    public Integer getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(Integer availableSlots) {
        this.availableSlots = availableSlots;
    }

    public String getStationStatus() {
        return stationStatus;
    }

    public void setStationStatus(String stationStatus) {
        this.stationStatus = stationStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
