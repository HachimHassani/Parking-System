package com.platform.parkingsystem.service;

import com.platform.parkingsystem.api.exceptions.ResourceNotFoundException;
import com.platform.parkingsystem.api.model.Payment;
import com.platform.parkingsystem.api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createPayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());
        payment.setComplete(false);
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(String id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isPresent()) {
            return payment.get();
        } else {
            throw new ResourceNotFoundException("Payment", "id", id);
        }
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment updatePayment(String id, Payment payment) {
        Payment existingPayment = getPaymentById(id);
        existingPayment.setAmount(payment.getAmount());
        existingPayment.setPaymentDate(payment.getPaymentDate());
        existingPayment.setComplete(payment.isComplete());
        return paymentRepository.save(existingPayment);
    }

    public void deletePayment(String id) {
        Payment existingPayment = getPaymentById(id);
        paymentRepository.delete(existingPayment);
    }
}
