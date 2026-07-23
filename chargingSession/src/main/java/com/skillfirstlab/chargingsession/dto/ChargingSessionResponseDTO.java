package com.skillfirstlab.chargingsession.dto;

import com.skillfirstlab.chargingsession.enums.ChargingStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargingSessionResponseDTO {
    private Long sessionId;
    private Long bookingId;
    private Long stationId;
    private Long customerId;
    private ChargingStatus status;
    private Integer batteryStartPercentage;
    private Integer batteryEndPercentage;
    private Double batteryCapacity; // ✅ NEW FIELD
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private Double energyConsumed;
    private Double totalCost;
    private Long durationMinutes;
}


