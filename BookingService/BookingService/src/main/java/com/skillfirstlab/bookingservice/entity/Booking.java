package com.skillfirstlab.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "booking")
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private String customerName;

    private String customerEmail;

    private String customerMobileNumber;

    private Long stationId;

    private Integer slotNumber;

    private LocalDate bookingDate;

    private LocalTime bookingTime;

    private String vehicleNumber;

    private String bookingStatus;

}