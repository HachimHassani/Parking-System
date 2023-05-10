package com.platform.parkingsystem.api.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "parkingspaces")
public class ParkingSpace {
    private Long id;
    private String spaceNumber;
    private Boolean available;

    private User user;

    private String parkingLotId;
    // getters and setters
    public ParkingSpace(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setSpaceNumber(String spaceNumber) {
        this.spaceNumber = spaceNumber;
    }

    public String getSpaceNumber() {
        return spaceNumber;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}