package com.skillfirstlab.paymentservice.controller;
import com.skillfirstlab.paymentservice.dto.PaymentRequestDTO;
import com.skillfirstlab.paymentservice.dto.PaymentResponseDTO;
import com.skillfirstlab.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/generate")
    public PaymentResponseDTO generatePayment(
            @Valid @RequestBody PaymentRequestDTO requestDTO) {
        return paymentService.generatePayment(requestDTO);
    }
    @GetMapping("/{paymentId}")
    public PaymentResponseDTO getPaymentById(
            @PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId);
    }
    @GetMapping
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }
    @DeleteMapping("/{paymentId}")
    public String deletePayment(
            @PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);

        return "Payment deleted successfully";
    }

}
