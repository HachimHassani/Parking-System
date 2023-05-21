package com.platform.parkingsystem.api.controller;

import com.platform.parkingsystem.api.model.Payment;
import com.platform.parkingsystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<Payment> createPayment(Authentication authentication, @RequestBody Payment payment) {
        String username = authentication.getName();

        Payment createdPayment = paymentService.createPaymentForUser(username, payment);
        return ResponseEntity.created(URI.create("/payments/" + createdPayment.getId())).body(createdPayment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();

        Payment payment = paymentService.getPaymentByIdForUser(id, username);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(payment);
    }

    @GetMapping("")
    public ResponseEntity<List<Payment>> getAllPayments(Authentication authentication) {
        String username = authentication.getName();

        List<Payment> payments = paymentService.getAllPaymentsForUser(username);
        return ResponseEntity.ok(payments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable String id, Authentication authentication, @RequestBody Payment payment) {
        String username = authentication.getName();

        Payment updatedPayment = paymentService.updatePaymentForUser(id, username, payment);
        if (updatedPayment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();

        boolean deleted = paymentService.deletePaymentForUser(id, username);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
