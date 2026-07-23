package com.skillfirstlab.chargingsession.service;

import com.skillfirstlab.chargingsession.dto.ChargingSessionRequestDTO;
import com.skillfirstlab.chargingsession.dto.ChargingSessionResponseDTO;

public interface ChargingSessionService {

    // Start a new charging session


    ChargingSessionResponseDTO startCharging(ChargingSessionRequestDTO requestDTO);

    ChargingSessionResponseDTO stopSession(
            Long sessionId,
            Integer batteryEndPercentage,
            String paymentMethod);

    ChargingSessionResponseDTO getSessionById(Long sessionId);

    Iterable<ChargingSessionResponseDTO> getAllSessions();

    String deleteSession(Long sessionId);
}