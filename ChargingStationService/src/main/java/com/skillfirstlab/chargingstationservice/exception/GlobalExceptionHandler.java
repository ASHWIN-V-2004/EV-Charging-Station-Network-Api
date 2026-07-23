package com.skillfirstlab.chargingstationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(StationNotFoundException.class)
    public ResponseEntity<String>
    handleStationNotFound(
            StationNotFoundException ex) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<String>
    handleValidationException(
            MethodArgumentNotValidException ex) {

        String message =
                Objects.requireNonNull(ex.getBindingResult()
                                .getFieldError())
                        .getDefaultMessage();

        return new ResponseEntity<>(
                message,
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String>
    handleException(Exception ex) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}