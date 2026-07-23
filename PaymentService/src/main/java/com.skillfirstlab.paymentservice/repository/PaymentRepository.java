package com.skillfirstlab.paymentservice.repository;

import com.skillfirstlab.paymentservice.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

}
