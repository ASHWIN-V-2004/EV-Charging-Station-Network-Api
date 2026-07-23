package com.skillfirstlab.chargingsession.service;
import com.skillfirstlab.chargingsession.dto.ChargingSessionRequestDTO;
import com.skillfirstlab.chargingsession.dto.ChargingSessionResponseDTO;
import com.skillfirstlab.chargingsession.dto.PaymentResponseDTO;
import com.skillfirstlab.chargingsession.dto.BookingResponseDTO;
import com.skillfirstlab.chargingsession.dto.ChargingStationResponseDTO;
import com.skillfirstlab.chargingsession.entity.ChargingSession;
import com.skillfirstlab.chargingsession.enums.ChargingStatus;
import com.skillfirstlab.chargingsession.exception.LowBatteryException;
import com.skillfirstlab.chargingsession.exception.ResourceNotFoundException;
import com.skillfirstlab.chargingsession.repository.ChargingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ChargingSessionServiceImpl implements ChargingSessionService {

    @Autowired
    private ChargingSessionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ChargingSessionResponseDTO startCharging(ChargingSessionRequestDTO requestDTO) {

        // Battery validation
        if (requestDTO.getBatteryStartPercentage() > 95) {
            throw new IllegalArgumentException(
                    "Charging cannot start because battery level is above 95%");
        }

        if (requestDTO.getBatteryStartPercentage() < 0) {
            throw new IllegalArgumentException(
                    "Battery percentage cannot be negative");
        }

        // Battery capacity validation
        if (requestDTO.getBatteryCapacity() <= 0) {
            throw new IllegalArgumentException(
                    "Battery capacity must be greater than zero");
        }

        // Check if another session is already running for the booking
        repository.findAll().forEach(session -> {
            if (session.getBookingId().equals(requestDTO.getBookingId())
                    && session.getStatus() == ChargingStatus.IN_PROGRESS) {

                throw new IllegalArgumentException(
                        "Charging session already in progress for this booking");
            }
        });

        // Verify Booking Service
        String bookingUrl =
                "http://localhost:8082/bookings/" + requestDTO.getBookingId();

        BookingResponseDTO booking;

        try {
            booking = restTemplate.getForObject(
                    bookingUrl,
                    BookingResponseDTO.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Booking not found");
        }

        // Verify Charging Station Service
        String stationUrl =
                "http://localhost:8081/stations/" + requestDTO.getStationId();

        ChargingStationResponseDTO station;

        try {
            station = restTemplate.getForObject(
                    stationUrl,
                    ChargingStationResponseDTO.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Charging Station not found");
        }
        if (!"BOOKED".equalsIgnoreCase(booking.getBookingStatus())) {
            throw new IllegalArgumentException("Booking is not active");
        }

        if (!booking.getStationId().equals(requestDTO.getStationId())) {
            throw new IllegalArgumentException("Booking belongs to another station");
        }

        if (!"ACTIVE".equalsIgnoreCase(station.getStationStatus())) {
            throw new IllegalArgumentException("Charging Station is not active");
        }

        if (!station.getConnectorType().equalsIgnoreCase(
                requestDTO.getConnectorType().name())) {
            throw new IllegalArgumentException("Connector type mismatch");
        }

        if (station.getAvailableSlots() <= 0) {
            throw new IllegalArgumentException("No charging slots available");
        }
        if (booking.getSlotNumber() > station.getAvailableSlots()) {
            throw new IllegalArgumentException(
                    "Invalid slot number. Station has only "
                            + station.getAvailableSlots() + " slots.");
        }

        ChargingSession session = new ChargingSession();

        session.setBookingId(requestDTO.getBookingId());
        session.setCustomerId(requestDTO.getCustomerId());
        session.setStationId(requestDTO.getStationId());
        session.setConnectorType(requestDTO.getConnectorType());
        session.setBatteryStartPercentage(requestDTO.getBatteryStartPercentage());
        session.setBatteryCapacity(requestDTO.getBatteryCapacity());
        session.setStatus(ChargingStatus.IN_PROGRESS);
        session.setStartTime(LocalDateTime.now());

        repository.save(session);

        return convertToDTO(session);
    }

    @Override
    public ChargingSessionResponseDTO stopSession(
            Long sessionId,
            Integer batteryEndPercentage,
            String paymentMethod) {

        ChargingSession session = repository.findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException("Charging Session not found"));

        // Session validation
        if (session.getStatus() != ChargingStatus.IN_PROGRESS) {
            throw new IllegalArgumentException(
                    "Charging session is already stopped");
        }

        // Battery validation
        if (batteryEndPercentage < session.getBatteryStartPercentage()) {
            throw new IllegalArgumentException(
                    "Battery end percentage cannot be less than battery start percentage");
        }

        if (batteryEndPercentage > 100) {
            throw new IllegalArgumentException(
                    "Battery percentage cannot exceed 100");
        }

        // Payment validation
        if (!(paymentMethod.equalsIgnoreCase("UPI")
                || paymentMethod.equalsIgnoreCase("CARD")
                || paymentMethod.equalsIgnoreCase("CASH"))) {

            throw new IllegalArgumentException(
                    "Payment method should be UPI, CARD or CASH");
        }

        session.setStopTime(LocalDateTime.now());
        session.setBatteryEndPercentage(batteryEndPercentage);

        long durationMinutes =
                Duration.between(session.getStartTime(),
                        session.getStopTime()).toMinutes();

        if (durationMinutes <= 0) {
            durationMinutes = 1;
        }

        session.setDurationMinutes(durationMinutes);

        double energyConsumed =
                session.getBatteryCapacity()
                        * (batteryEndPercentage
                        - session.getBatteryStartPercentage()) / 100.0;

        if (energyConsumed <= 0) {
            throw new IllegalArgumentException(
                    "Energy consumed must be greater than zero");
        }

        session.setEnergyConsumed(energyConsumed);

        if (batteryEndPercentage == 100) {
            session.setStatus(ChargingStatus.COMPLETED);
        } else {
            session.setStatus(ChargingStatus.STOPPED);
        }

        double totalCost = energyConsumed * 15;

        session.setTotalCost(totalCost);

        repository.save(session);

        Map<String, Object> paymentRequest = new HashMap<>();

        paymentRequest.put("sessionId", session.getSessionId());
        paymentRequest.put("customerId", session.getCustomerId());
        paymentRequest.put("amount", totalCost);
        paymentRequest.put("paymentMethod", paymentMethod);

        String paymentUrl =
                "http://localhost:8084/payments/generate";

        PaymentResponseDTO paymentResponse =
                restTemplate.postForObject(
                        paymentUrl,
                        paymentRequest,
                        PaymentResponseDTO.class);

        if (paymentResponse == null) {
            throw new RuntimeException("Payment generation failed");
        }

        return convertToDTO(session);
    }

    @Override
    public ChargingSessionResponseDTO getSessionById(Long sessionId) {
        ChargingSession session = repository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Charging Session not found"));
        return convertToDTO(session);
    }

    @Override
    public Iterable<ChargingSessionResponseDTO> getAllSessions() {
        List<ChargingSessionResponseDTO> responseList = new ArrayList<>();
        repository.findAll().forEach(session -> responseList.add(convertToDTO(session)));
        return responseList;
    }

    @Override
    public String deleteSession(Long sessionId) {
        if (!repository.existsById(sessionId)) {
            throw new RuntimeException("Charging Session not found");
        }
        repository.deleteById(sessionId);
        return "Charging Session deleted successfully";
    }

    // Helper method
    private ChargingSessionResponseDTO convertToDTO(ChargingSession session) {
        ChargingSessionResponseDTO dto = new ChargingSessionResponseDTO();
        dto.setSessionId(session.getSessionId());
        dto.setBookingId(session.getBookingId());
        dto.setStationId(session.getStationId());
        dto.setCustomerId(session.getCustomerId());
        dto.setStatus(session.getStatus());
        dto.setBatteryStartPercentage(session.getBatteryStartPercentage());
        dto.setBatteryEndPercentage(session.getBatteryEndPercentage());
        dto.setBatteryCapacity(session.getBatteryCapacity());
        dto.setStartTime(session.getStartTime());
        dto.setStopTime(session.getStopTime());
        dto.setDurationMinutes(session.getDurationMinutes());
        dto.setEnergyConsumed(session.getEnergyConsumed());
        dto.setTotalCost(session.getTotalCost());
        return dto;
    }
}


