package com.skillfirstlab.chargingsession.controller;
import com.skillfirstlab.chargingsession.dto.ChargingSessionRequestDTO;
import com.skillfirstlab.chargingsession.dto.ChargingSessionResponseDTO;
import com.skillfirstlab.chargingsession.dto.StopSessionRequestDTO;
import com.skillfirstlab.chargingsession.service.ChargingSessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/sessions")
public class ChargingSessionController {
    @Autowired
    private ChargingSessionService chargingSessionService;
    @PostMapping("/start")
    public ChargingSessionResponseDTO startCharging(
            @Valid @RequestBody ChargingSessionRequestDTO requestDTO) {
        return chargingSessionService.startCharging(requestDTO);
    }
    @PutMapping("/stop/{sessionId}")
    public ChargingSessionResponseDTO stopSession(
            @PathVariable Long sessionId,
            @Valid @RequestBody StopSessionRequestDTO requestDTO) {

        return chargingSessionService.stopSession(
                sessionId,
                requestDTO.getBatteryEndPercentage(),
                requestDTO.getPaymentMethod());
    }
    @GetMapping("/{sessionId}")
    public ChargingSessionResponseDTO getSessionById(
            @PathVariable Long sessionId) {
        return chargingSessionService.getSessionById(sessionId);
    }
    @GetMapping
    public Iterable<ChargingSessionResponseDTO> getAllSessions() {

        return chargingSessionService.getAllSessions();
    }
    @DeleteMapping("/{sessionId}")
    public String deleteSession(@PathVariable Long sessionId) {
        return chargingSessionService.deleteSession(sessionId);
    }
}
