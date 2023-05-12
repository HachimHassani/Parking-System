package com.platform.parkingsystem.api.repository;

import com.platform.parkingsystem.api.model.ParkingSpace;
import com.platform.parkingsystem.api.model.Reservation;
import com.platform.parkingsystem.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    List<Reservation> findByActiveTrue();

    List<Reservation> findByParkingSpaceAndActiveTrue(ParkingSpace parkingSpace);

    List<Reservation> findByUserAndActiveTrue(User user);

    List<Reservation> findRecentReservationsByUser(User user);
    List<Reservation> findByParkingSpace(ParkingSpace parkingSpace);


     List<Reservation> findByFromBeforeAndToAfterAndParkingSpaceAndActiveTrue(LocalDateTime start, LocalDateTime end, ParkingSpace parkingSpace);

    List<Reservation> findByFromBeforeAndToAfterAndUserAndActiveTrue(LocalDateTime start, LocalDateTime end, User user);

    Optional<Reservation> findByUserAndParkingSpaceAndActiveTrue(User user, ParkingSpace parkingSpace);


}
