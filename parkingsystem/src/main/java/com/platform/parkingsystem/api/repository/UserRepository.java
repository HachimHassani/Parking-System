package com.platform.parkingsystem.api.repository;

import com.platform.parkingsystem.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByLicensePlate(String licensePlate);

    public User findByUsername(String name);

    Boolean existsByEmail(String email);




}