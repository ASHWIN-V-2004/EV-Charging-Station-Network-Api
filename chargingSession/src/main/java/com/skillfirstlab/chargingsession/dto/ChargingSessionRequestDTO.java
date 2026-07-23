package com.skillfirstlab.chargingsession.dto;
import com.skillfirstlab.chargingsession.enums.ConnectorType;

import jakarta.validation.constraints.*;

public class ChargingSessionRequestDTO {

    @NotNull(message = "Booking Id is required")
    private Long bookingId;

    @NotNull(message = "Station Id is required")
    private Long stationId;

    @NotNull(message = "Customer Id is required")
    private Long customerId;

    @NotNull(message = "Connector Type is required")
    private ConnectorType connectorType;

    @NotNull(message = "Battery Percentage is required")
    @Min(value = 0, message = "Battery percentage cannot be less than 0")
    @Max(value = 100, message = "Battery percentage cannot exceed 100")
    private Integer batteryStartPercentage;

    @NotNull(message = "Battery Capacity is required") // ✅ NEW FIELD
    @Min(value = 1, message = "Battery capacity must be positive")
    private Double batteryCapacity; // in kWh

    // Getters and Setters
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getStationId() { return stationId; }
    public void setStationId(Long stationId) { this.stationId = stationId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public ConnectorType getConnectorType() { return connectorType; }
    public void setConnectorType(ConnectorType connectorType) { this.connectorType = connectorType; }

    public Integer getBatteryStartPercentage() { return batteryStartPercentage; }
    public void setBatteryStartPercentage(Integer batteryStartPercentage) {
        this.batteryStartPercentage = batteryStartPercentage;
    }

    public Double getBatteryCapacity() { return batteryCapacity; }
    public void setBatteryCapacity(Double batteryCapacity) { this.batteryCapacity = batteryCapacity; }
}

