package com.skillfirstlab.bookingservice.exception;

public class DuplicateBookingException
        extends RuntimeException{


    public DuplicateBookingException(String message){
        super(message);
    }

}