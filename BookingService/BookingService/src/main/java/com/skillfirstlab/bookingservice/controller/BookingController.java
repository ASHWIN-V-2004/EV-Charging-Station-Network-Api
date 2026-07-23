package com.skillfirstlab.bookingservice.controller;

import com.skillfirstlab.bookingservice.dto.*;
import com.skillfirstlab.bookingservice.entity.Booking;
import com.skillfirstlab.bookingservice.service.BookingService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Slf4j
public class BookingController {

    private final BookingService service;


    public BookingController(
            BookingService service){

        this.service = service;
    }


    @PostMapping
    public BookingResponseDTO createBooking(

            @Valid
            @RequestBody
            BookingRequestDTO dto){

        log.info("POST API called.");

        return service.createBooking(dto);
    }



    @GetMapping
    public List<Booking> getAllBookings(){

        return service.getAllBookings();
    }


    @GetMapping("/{id}")
    public BookingResponseDTO getBookingById(@PathVariable Long id) {

        return service.getBookingById(id);
    }

    @PutMapping("/{id}/cancel")
    public String cancelBooking(
            @PathVariable Long id){

        return service.cancelBooking(id);
    }



    @DeleteMapping("/{id}")
    public String deleteBooking(
            @PathVariable Long id){

        return service.deleteBooking(id);
    }

}