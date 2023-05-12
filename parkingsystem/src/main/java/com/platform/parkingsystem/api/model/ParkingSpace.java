package com.platform.parkingsystem.api.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "parkingspaces")
public class ParkingSpace {
    private String id;
    private String spaceNumber;
    private Boolean available = true;



    @DBRef(lazy = true)
    private ParkingLot parkingLot;
    // getters and setters
    public ParkingSpace(){

    }
    public ParkingSpace(String name){
        this.spaceNumber = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void setSpaceNumber(String spaceNumber) {
        this.spaceNumber = spaceNumber;
    }

    public String getSpaceNumber() {
        return spaceNumber;
    }


}