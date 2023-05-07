package com.platform.parkingsystem.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reservations")
public class Reservation {

    @Id
    private String id;

    @DBRef(lazy = true)
    private User user;

    @DBRef(lazy = true)
    private ParkingSpace parkingSpace;

    private LocalDateTime from;

    private LocalDateTime to;

    private boolean active;

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
}
