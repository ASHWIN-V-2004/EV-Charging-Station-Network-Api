package com.skillfirstlab.bookingservice.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(
            BookingNotFoundException.class
    )
    public ResponseEntity<String>
    handleBookingNotFound(
            BookingNotFoundException ex){

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );

    }



    @ExceptionHandler(
            DuplicateBookingException.class
    )
    public ResponseEntity<String>
    handleDuplicateBooking(
            DuplicateBookingException ex){

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );

    }



    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<String>
    handleValidationException(
            MethodArgumentNotValidException ex){

        String message =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        return new ResponseEntity<>(
                message,
                HttpStatus.BAD_REQUEST
        );

    }

}