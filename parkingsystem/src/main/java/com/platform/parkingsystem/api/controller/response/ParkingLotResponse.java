package com.platform.parkingsystem.api.controller.response;

public class ParkingLotResponse {
    private String id;
    private String name;
    private int capacity;
    private String city;

    // Add any other relevant fields

    // Create getters and setters
    // ...


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public int getCapacity() {
        return capacity;
    }
}