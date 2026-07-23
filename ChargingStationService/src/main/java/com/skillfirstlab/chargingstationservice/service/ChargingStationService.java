package com.skillfirstlab.chargingstationservice.service;

import com.skillfirstlab.chargingstationservice.dto.*;
import com.skillfirstlab.chargingstationservice.entity.ChargingStation;
import com.skillfirstlab.chargingstationservice.exception.StationNotFoundException;
import com.skillfirstlab.chargingstationservice.repository.ChargingStationRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

        @Service
        @Slf4j

        public class ChargingStationService {

            private final ChargingStationRepository repository;

            public ChargingStationService(ChargingStationRepository repository) {
                this.repository = repository;
            }


            public ChargingStationResponseDTO addStation(
                    ChargingStationRequestDTO dto) {

                ChargingStation station = new ChargingStation();

                station.setStationName(dto.getStationName());
                station.setCity(dto.getCity());
                station.setLocation(dto.getLocation());
                station.setConnectorType(dto.getConnectorType());
                station.setChargingSpeed(dto.getChargingSpeed());
                station.setAvailableSlots(dto.getAvailableSlots());
                station.setStationStatus(dto.getStationStatus());

                ChargingStation savedStation =
                        repository.save(station);

                ChargingStationResponseDTO response = new ChargingStationResponseDTO();

                response.setStationId(savedStation.getStationId());
                response.setStationName(savedStation.getStationName());
                response.setCity(savedStation.getCity());
                response.setLocation(savedStation.getLocation());
                response.setConnectorType(savedStation.getConnectorType());
                response.setChargingSpeed(savedStation.getChargingSpeed());
                response.setAvailableSlots(savedStation.getAvailableSlots());
                response.setStationStatus(savedStation.getStationStatus());
                response.setMessage("Charging Station Created Successfully");

                return response;
            }


            public List<ChargingStation> getAllStations() {
                return (List<ChargingStation>) repository.findAll();
            }

            public ChargingStationResponseDTO getStationById(Long id) {

                ChargingStation station = repository.findById(id)
                        .orElseThrow(() ->
                                new StationNotFoundException("Station Not Found"));

                ChargingStationResponseDTO response = new ChargingStationResponseDTO();

                response.setStationId(station.getStationId());
                response.setStationName(station.getStationName());
                response.setCity(station.getCity());
                response.setLocation(station.getLocation());
                response.setConnectorType(station.getConnectorType());
                response.setChargingSpeed(station.getChargingSpeed());
                response.setAvailableSlots(station.getAvailableSlots());
                response.setStationStatus(station.getStationStatus());
                response.setMessage("Station Found Successfully");

                return response;
            }



            public ChargingStation updateStation(
                    Long id,
                    ChargingStationRequestDTO dto) {

                ChargingStation station = repository.findById(id)
                        .orElseThrow(() ->
                                new StationNotFoundException("Station Not Found"));

        station.setStationName(dto.getStationName());
        station.setCity(dto.getCity());
        station.setLocation(dto.getLocation());
        station.setConnectorType(dto.getConnectorType());
        station.setChargingSpeed(dto.getChargingSpeed());
        station.setAvailableSlots(dto.getAvailableSlots());
        station.setStationStatus(dto.getStationStatus());

        return repository.save(station);
    }


    public String deleteStation(Long id) {
        ChargingStation station = repository.findById(id)
                .orElseThrow(() ->
                        new StationNotFoundException("Station Not Found"));

        repository.delete(station);

        return "Station Deleted Successfully";
    }
    public String occupySlot(Long id) {

        ChargingStation station = repository.findById(id)
                .orElseThrow(() ->
                        new StationNotFoundException("Station Not Found"));
        if (station.getAvailableSlots() <= 0) {
            throw new RuntimeException("No Slots Available");
        }

        station.setAvailableSlots(
                station.getAvailableSlots() - 1);

        repository.save(station);

        return "Slot Occupied";
    }
    public String releaseSlot(Long id) {

        ChargingStation station = repository.findById(id)
                .orElseThrow(() ->
                        new StationNotFoundException("Station Not Found"));

        station.setAvailableSlots(
                station.getAvailableSlots() + 1);

        repository.save(station);

        return "Slot Released";
    }

}