package com.skillfirstlab.chargingsession.exception;


public class LowBatteryException extends RuntimeException {

    public LowBatteryException(String message) {
        super(message);
    }
}