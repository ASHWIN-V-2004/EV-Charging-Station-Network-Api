package com.skillfirstlab.paymentservice.service;


import com.skillfirstlab.paymentservice.dto.PaymentRequestDTO;
import com.skillfirstlab.paymentservice.dto.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

    PaymentResponseDTO generatePayment(PaymentRequestDTO requestDTO);

    PaymentResponseDTO getPaymentById(Long paymentId);

    List<PaymentResponseDTO> getAllPayments();

    void deletePayment(Long paymentId);
}
