package com.platform.parkingsystem.api.repository;

import com.platform.parkingsystem.api.model.ParkingSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpaceRepository extends MongoRepository<ParkingSpace, String> {

    List<ParkingSpace> findByParkingLotId(String parkingLotId);

    ParkingSpace findByParkingLotIdAndId(String parkingLotId, String parkingSpaceId);



}