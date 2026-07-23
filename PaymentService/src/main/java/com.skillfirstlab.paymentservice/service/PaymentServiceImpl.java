package com.skillfirstlab.paymentservice.service;

import com.skillfirstlab.paymentservice.dto.PaymentRequestDTO;
import com.skillfirstlab.paymentservice.dto.PaymentResponseDTO;
import com.skillfirstlab.paymentservice.entity.Payment;
import com.skillfirstlab.paymentservice.enums.PaymentStatus;
import com.skillfirstlab.paymentservice.exception.PaymentFailedException;
import com.skillfirstlab.paymentservice.exception.ResourceNotFoundException;
import com.skillfirstlab.paymentservice.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository repository;

    @Override
    public PaymentResponseDTO generatePayment(PaymentRequestDTO requestDTO) {

        logger.info("Generating payment for Session ID : {}", requestDTO.getSessionId());

        if(requestDTO.getAmount() <= 0){
            throw new PaymentFailedException("Payment amount must be greater than zero");
        }

        Payment payment = new Payment();

        payment.setSessionId(requestDTO.getSessionId());
        payment.setCustomerId(requestDTO.getCustomerId());
        payment.setAmount(requestDTO.getAmount());
        payment.setPaymentMethod(requestDTO.getPaymentMethod());

        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        payment.setInvoiceNumber("INV-" +
                UUID.randomUUID().toString().substring(0,8));

        payment.setPaymentDateTime(LocalDateTime.now());

        Payment savedPayment = repository.save(payment);

        logger.info("Payment generated successfully");

        return convertToDTO(savedPayment);
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long paymentId) {

        Payment payment = repository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));

        logger.info("Retrieved payment {}", paymentId);

        return convertToDTO(payment);
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {

        Iterable<Payment> payments = repository.findAll();

        List<PaymentResponseDTO> paymentList = new ArrayList<>();

        for(Payment payment : payments){
            paymentList.add(convertToDTO(payment));
        }

        logger.info("Retrieved all payments");

        return paymentList;
    }

    @Override
    public void deletePayment(Long paymentId) {

        Payment payment = repository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));

        repository.delete(payment);

        logger.info("Payment deleted successfully");
    }

    private PaymentResponseDTO convertToDTO(Payment payment){

        PaymentResponseDTO dto = new PaymentResponseDTO();

        dto.setPaymentId(payment.getPaymentId());
        dto.setSessionId(payment.getSessionId());
        dto.setCustomerId(payment.getCustomerId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setInvoiceNumber(payment.getInvoiceNumber());
        dto.setPaymentDateTime(payment.getPaymentDateTime());

        return dto;
    }
}