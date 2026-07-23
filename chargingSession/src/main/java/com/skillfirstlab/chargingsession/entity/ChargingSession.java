package com.skillfirstlab.chargingsession.entity;

import com.skillfirstlab.chargingsession.enums.ChargingStatus;
import com.skillfirstlab.chargingsession.enums.ConnectorType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "charging_session")
public class ChargingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @Column(nullable = false)
    private Long bookingId;

    @Column(nullable = false)
    private Long stationId;

    @Column(nullable = false)
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConnectorType connectorType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChargingStatus status = ChargingStatus.IN_PROGRESS; // default

    @Column(nullable = false)
    private Integer batteryStartPercentage;

    private Integer batteryEndPercentage;

    // ✅ NEW FIELD for energy calculation
    @Column(nullable = false)
    private Double batteryCapacity; // in kWh

    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private Long durationMinutes;

    private Double energyConsumed;
    private Double totalCost;


    // Default constructor
    public ChargingSession() {}

    // Getters and setters
    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getStationId() { return stationId; }
    public void setStationId(Long stationId) { this.stationId = stationId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public ConnectorType getConnectorType() { return connectorType; }
    public void setConnectorType(ConnectorType connectorType) { this.connectorType = connectorType; }

    public ChargingStatus getStatus() { return status; }
    public void setStatus(ChargingStatus status) { this.status = status; }

    public Integer getBatteryStartPercentage() { return batteryStartPercentage; }
    public void setBatteryStartPercentage(Integer batteryStartPercentage) { this.batteryStartPercentage = batteryStartPercentage; }

    public Integer getBatteryEndPercentage() { return batteryEndPercentage; }
    public void setBatteryEndPercentage(Integer batteryEndPercentage) { this.batteryEndPercentage = batteryEndPercentage; }

    public Double getBatteryCapacity() { return batteryCapacity; }
    public void setBatteryCapacity(Double batteryCapacity) { this.batteryCapacity = batteryCapacity; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getStopTime() { return stopTime; }
    public void setStopTime(LocalDateTime stopTime) { this.stopTime = stopTime; }

    public Long getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Long durationMinutes) { this.durationMinutes = durationMinutes; }

    public Double getEnergyConsumed() { return energyConsumed; }
    public void setEnergyConsumed(Double energyConsumed) { this.energyConsumed = energyConsumed; }

    public Double getTotalCost() { return totalCost; }
    public void setTotalCost(Double totalCost) { this.totalCost = totalCost; }
}

