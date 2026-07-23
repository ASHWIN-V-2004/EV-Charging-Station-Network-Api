package com.skillfirstlab.chargingstationservice.controller;

import com.skillfirstlab.chargingstationservice.dto.*;
import com.skillfirstlab.chargingstationservice.entity.ChargingStation;
import com.skillfirstlab.chargingstationservice.service.ChargingStationService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")
public class ChargingStationController {

    private final ChargingStationService service;


    public ChargingStationController(
            ChargingStationService service) {
        this.service = service;
    }


    @PostMapping
    public ChargingStationResponseDTO addStation(
            @Valid @RequestBody
            ChargingStationRequestDTO dto) {

        return service.addStation(dto);
    }


    @GetMapping
    public List<ChargingStation> getAllStations() {
        return service.getAllStations();
    }


    @GetMapping("/{id}")
    public ChargingStationResponseDTO getStationById(@PathVariable Long id){

        return service.getStationById(id);
    }


    @PutMapping("/{id}")
    public ChargingStation updateStation(
            @PathVariable Long id,
            @Valid @RequestBody
            ChargingStationRequestDTO dto) {

        return service.updateStation(id, dto);
    }
    @PutMapping("/{id}/occupy")
    public String occupySlot(@PathVariable Long id) {
        return service.occupySlot(id);
    }

    @PutMapping("/{id}/release")
    public String releaseSlot(@PathVariable Long id) {
        return service.releaseSlot(id);
    }

    @DeleteMapping("/{id}")
    public String deleteStation(
            @PathVariable Long id) {

        return service.deleteStation(id);
    }

}