package com.platform.parkingsystem.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "entries")
public class Entry {

    @Id
    private String id;

    private String licensePlate;

    @DBRef(lazy = true)
    private ParkingLot parkingLot;

    private LocalDateTime time;





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
