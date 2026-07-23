package com.skillfirstlab.bookingservice.service;

import com.skillfirstlab.bookingservice.dto.*;
import com.skillfirstlab.bookingservice.entity.Booking;
import com.skillfirstlab.bookingservice.exception.*;
import com.skillfirstlab.bookingservice.repository.BookingRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookingService {

    private final BookingRepository repository;


    public BookingService(
            BookingRepository repository) {

        this.repository = repository;
    }



    public BookingResponseDTO createBooking(
            BookingRequestDTO dto){


        log.info("Inside createBooking() method");


        boolean alreadyBooked =
                repository.existsByStationIdAndSlotNumberAndBookingDateAndBookingTime(

                        dto.getStationId(),
                        dto.getSlotNumber(),
                        dto.getBookingDate(),
                        dto.getBookingTime()
                );


        if(alreadyBooked){

            throw new DuplicateBookingException(
                    "Slot is already booked."
            );

        }


        Booking booking = new Booking();

        booking.setCustomerName(dto.getCustomerName());
        booking.setCustomerEmail(dto.getCustomerEmail());
        booking.setCustomerMobileNumber(
                dto.getCustomerMobileNumber()
        );
        booking.setStationId(dto.getStationId());
        booking.setSlotNumber(dto.getSlotNumber());
        booking.setBookingDate(dto.getBookingDate());
        booking.setBookingTime(dto.getBookingTime());
        booking.setVehicleNumber(dto.getVehicleNumber());

        booking.setBookingStatus("BOOKED");


        Booking savedBooking =
                repository.save(booking);


        BookingResponseDTO response = new BookingResponseDTO();

        response.setBookingId(savedBooking.getBookingId());
        response.setStationId(savedBooking.getStationId());
        response.setSlotNumber(savedBooking.getSlotNumber());
        response.setBookingStatus(savedBooking.getBookingStatus());
        response.setMessage("Booking Created Successfully");

        return response;

    }



    public List<Booking> getAllBookings(){

        log.info("Fetching all bookings.");

        return repository.findAll();
    }



    public BookingResponseDTO getBookingById(Long id) {

        Booking booking = repository.findById(id)
                .orElseThrow(() ->
                        new BookingNotFoundException(
                                "Booking Not Found."));

        BookingResponseDTO response = new BookingResponseDTO();

        response.setBookingId(booking.getBookingId());
        response.setStationId(booking.getStationId());
        response.setSlotNumber(booking.getSlotNumber());
        response.setBookingStatus(booking.getBookingStatus());
        response.setMessage("Booking Found Successfully");

        return response;
    }




    public String cancelBooking(Long id) {

        Booking booking = repository.findById(id)
                .orElseThrow(() ->
                        new BookingNotFoundException("Booking Not Found."));

        booking.setBookingStatus("CANCELLED");

        repository.save(booking);

        return "Booking Cancelled Successfully.";
    }

    public String deleteBooking(Long id) {

        Booking booking = repository.findById(id)
                .orElseThrow(() ->
                        new BookingNotFoundException("Booking Not Found."));

        repository.delete(booking);

        return "Booking Deleted Successfully.";
    }




}