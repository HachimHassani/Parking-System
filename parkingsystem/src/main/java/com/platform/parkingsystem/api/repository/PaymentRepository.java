package com.platform.parkingsystem.api.repository;

import com.platform.parkingsystem.api.model.Payment;
import com.platform.parkingsystem.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
    Payment findByIdAndUser(String id, User user);
    List<Payment> findAllByUser(User User);
}
