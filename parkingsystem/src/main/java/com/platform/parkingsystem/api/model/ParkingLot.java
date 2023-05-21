package com.platform.parkingsystem.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.parkingsystem.api.repository.ReservationRepository;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "parking_lots")
public class ParkingLot {

    @Id
    private String id;

    @NotBlank
    private String name;


    private Double parkingFee;
    private int availableSpaces;

    private int capacity;
    private String city;

    @JsonIgnore
    @CreatedDate
    private LocalDateTime createdAt;
    @JsonIgnore
    @DBRef(lazy = true)
    private List<ParkingSpace> parkingSpaces;

    public ParkingLot(String name, int capacity, Double parkingFee, String city) {
        this.name = name;
        this.parkingFee = parkingFee;
        this.capacity = capacity;
        this.availableSpaces = capacity;
        this.city = city;
    }

    public ParkingLot(){

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
        // Check if there is at least one available space in the parking lot
        return availableSpaces > 0;
    }

    public Boolean reserveSpace() {
        // Check if there is an available space in the parking lot
        if (isSpaceAvailable()) {

            availableSpaces--;

            return true;
        }
        return false;
    }

    public Boolean grantAccess() {
        // Check if there are any reserved spaces in the parking lot
        if (isSpaceAvailable()) {
            // Decrement the availale spaces count
            availableSpaces--;
            return true;
        }
        return false;
    }

    public Boolean releaseSpace() {
        // Check if there are any occupied spaces in the parking lot
        if (capacity > availableSpaces) {
            // increment the availableSpaces count

            availableSpaces++;
            return true;
        }
        return false;
    }

    public Boolean updateOccupancy() {

        return true;
    }

    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setAvailableSpaces(int availableSpaces) {
        this.availableSpaces = availableSpaces;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAvailableSpaces() {
        return availableSpaces;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getCity() {
        return city;
    }



}