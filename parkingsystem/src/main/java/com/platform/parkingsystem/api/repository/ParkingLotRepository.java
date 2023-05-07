package com.platform.parkingsystem.api.repository;

import com.platform.parkingsystem.api.model.ParkingLot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends MongoRepository<ParkingLot, String> {

}