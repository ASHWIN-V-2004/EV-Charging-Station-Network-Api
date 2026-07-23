package com.skillfirstlab.chargingsession.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StopSessionRequestDTO {

    @NotNull(message = "Battery End Percentage is required")
    @Min(value = 0, message = "Battery percentage cannot be less than 0")
    @Max(value = 100, message = "Battery percentage cannot exceed 100")
    private Integer batteryEndPercentage;

    @NotBlank(message = "Payment Method is required")
    private String paymentMethod;

    public Integer getBatteryEndPercentage() {
        return batteryEndPercentage;
    }

    public void setBatteryEndPercentage(Integer batteryEndPercentage) {
        this.batteryEndPercentage = batteryEndPercentage;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
