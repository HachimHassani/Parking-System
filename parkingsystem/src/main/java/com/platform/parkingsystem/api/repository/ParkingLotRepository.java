package com.platform.parkingsystem.api.repository;

import com.platform.parkingsystem.api.model.ParkingLot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository extends MongoRepository<ParkingLot, String> {
    List<ParkingLot> findByCity(String city);

    List<ParkingLot> findByName(String name);

}