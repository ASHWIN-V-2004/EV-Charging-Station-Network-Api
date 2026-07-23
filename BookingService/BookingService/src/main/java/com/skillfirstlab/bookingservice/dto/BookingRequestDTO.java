package com.skillfirstlab.bookingservice.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class BookingRequestDTO {

    @NotBlank(message = "Customer name is required.")
    private String customerName;

    @Email(message = "Please enter a valid email address.")
    @NotBlank(message = "Customer email is required.")
    private String customerEmail;

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile number should contain exactly 10 digits."
    )
    private String customerMobileNumber;

    @NotNull(message = "Station ID is required.")
    private Long stationId;

    @Min(value = 1, message = "Slot number should be greater than 0.")
    private Integer slotNumber;

    @FutureOrPresent(message = "Booking date cannot be in the past.")
    private LocalDate bookingDate;

    @NotNull(message = "Booking time is required.")
    private LocalTime bookingTime;

    @NotBlank(message = "Vehicle number is required.")
    private String vehicleNumber;


    // Getter and Setter for customerName


    // Getter and Setter for customerEmail


    // Getter and Setter for customerMobileNumber


    // Getter and Setter for stationId


    // Getter and Setter for slotNumber


    // Getter and Setter for bookingDate


    // Getter and Setter for bookingTime


    // Getter and Setter for vehicleNumber

}