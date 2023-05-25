package com.platform.parkingsystem.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reservations")
public class Reservation {

    @Id
    private String id;

    @JsonIgnore
    @DBRef(lazy = true)
    private User user;

    @JsonIgnore
    @DBRef(lazy = true)
    private ParkingSpace parkingSpace;

    private LocalDateTime from;

    private LocalDateTime to;

    private boolean active;

    public Reservation(){

    }
    public Reservation(User user, ParkingSpace parkingSpace, LocalDateTime from, LocalDateTime to) {
        this.user = user;
        this.parkingSpace = parkingSpace;
        this.from = from;
        this.to = to;
        this.active = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getSpaceName() {
        return this.parkingSpace.getSpaceNumber();
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void usesParkingSpace(){
        this.parkingSpace.setAvailable(false);
    }

    public void freesParkingSpace(){
        this.parkingSpace.setAvailable(true);
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }
}
