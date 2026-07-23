package com.skillfirstlab.bookingservice.repository;

import com.skillfirstlab.bookingservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface BookingRepository
        extends JpaRepository<Booking,Long> {


    boolean existsByStationIdAndSlotNumberAndBookingDateAndBookingTime(
            Long stationId,
            Integer slotNumber,
            LocalDate bookingDate,
            LocalTime bookingTime
    );


}