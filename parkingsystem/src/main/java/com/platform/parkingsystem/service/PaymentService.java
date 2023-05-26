package com.platform.parkingsystem.service;

import com.platform.parkingsystem.api.exceptions.ResourceNotFoundException;
import com.platform.parkingsystem.api.model.Payment;
import com.platform.parkingsystem.api.model.User;
import com.platform.parkingsystem.api.repository.PaymentRepository;
import com.platform.parkingsystem.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    public Payment createPaymentForUser( String username,Payment payment) {
        Optional<User> userop = userRepository.findUserByEmail(username);
        if (userop.isPresent()){
            User user = userop.get();
            payment.setPaymentDate(LocalDateTime.now());
            payment.setComplete(false);
            payment.setUser(user);
            return paymentRepository.save(payment);
        }
        return null;

    }

    public Payment getPaymentByIdForUser(String id, String username) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isPresent()) {
            return payment.get();
        } else {
            throw new ResourceNotFoundException("Payment", "id", id);
        }
    }

    public List<Payment> getAllPaymentsForUser( String username) {
        Optional<User> userop = userRepository.findUserByEmail(username);
        if (userop.isPresent()) {
            User user = userop.get();
            return paymentRepository.findAllByUser(user);
        }
         return null;
    }

    public Payment updatePaymentForUser(String id, String username, Payment payment) {
        Payment existingPayment = getPaymentByIdForUser(id,username);
        existingPayment.setAmount(payment.getAmount());
        existingPayment.setPaymentDate(payment.getPaymentDate());
        existingPayment.setComplete(payment.isComplete());
        return paymentRepository.save(existingPayment);
    }

    public boolean deletePaymentForUser(String id, String username) {
        Optional<User> userop = userRepository.findUserByEmail(username);
        if (userop.isPresent()){
            User user = userop.get();
        // Retrieve the payment by ID and username
        Payment payment = paymentRepository.findByIdAndUser(id, user);
        if (payment == null) {
            return false; // Payment not found for the user
        }

        // Delete the payment from the repository
        paymentRepository.delete(payment);
        return true;
    }
        return false;
    }
}
