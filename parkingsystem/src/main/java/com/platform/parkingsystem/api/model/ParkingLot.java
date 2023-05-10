package com.platform.parkingsystem.api.model;


import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "parking_lots")
public class ParkingLot {

    @Id
    private String id;

    @NotBlank
    private String name;


    private Double parkingFee;

    private String city;

    @DBRef(lazy = true)
    private List<ParkingSpace> parkingSpaces;
    public ParkingLot(String name, int capacity, Double parkingFee) {
        this.name = name;
        this.parkingFee = parkingFee;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Double getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(Double parkingFee) {
        this.parkingFee = parkingFee;
    }

    // Methods
    public Boolean isSpaceAvailable() {
        // Implementation
        return true;
    }

    public Boolean reserveSpace() {
        // Implementation
        return true;
    }

    public Boolean grantAccess() {
        // Implementation
        return true;
    }

    public Boolean releaseSpace() {
        // Implementation
        return true;
    }

    public Boolean updateOccupancy() {
        // Implementation
        return true;
    }
}