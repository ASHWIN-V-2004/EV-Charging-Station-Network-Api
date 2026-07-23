package com.skillfirstlab.bookingservice.exception;

public class SlotUnavailableException
        extends RuntimeException{


    public SlotUnavailableException(String message){
        super(message);
    }

}