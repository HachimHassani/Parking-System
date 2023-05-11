package com.platform.parkingsystem.api.controller;

import com.platform.parkingsystem.api.model.ParkingLot;
import com.platform.parkingsystem.api.model.ParkingSpace;
import com.platform.parkingsystem.api.model.Reservation;
import com.platform.parkingsystem.api.model.User;
import com.platform.parkingsystem.service.ParkingLotService;
import com.platform.parkingsystem.service.ReservationService;
import com.platform.parkingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping("")
    public ResponseEntity<Reservation> createReservation(
            @RequestBody Reservation reservation,
            @RequestParam("user_id") String userId,
            @RequestParam("parking_lot_id") String parkinglotId,
            @RequestParam("from") String from,
            @RequestParam("to") String to) {


        LocalDateTime start = LocalDateTime.parse(from);
        LocalDateTime end = LocalDateTime.parse(to);


        User user = userService.getUserById(userId);

        ParkingLot parkingLot = parkingLotService.getParkingLotById(parkinglotId);

        // Check if the time slot is available
        if (!reservationService.isTimeSlotAvailable(parkingLot, start, end)) {
            return ResponseEntity.badRequest().body(null);
        }
        ParkingSpace parkingSpace = reservationService.chooseparkingspace(parkingLot, start, end);
        // Create the reservation
        reservation.setUser(user);
        reservation.setParkingSpace(parkingSpace);
        reservation.setFrom(start);
        reservation.setTo(end);
        reservation.setActive(true);
        Reservation createdReservation = reservationService.createReservation(reservation);

        return ResponseEntity.ok(createdReservation);
    }

    @GetMapping()
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable String id, @RequestBody Reservation reservation) {
        reservation.setId(id);
        Reservation updatedReservation = reservationService.updateReservation(reservation);
        if (updatedReservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
        boolean deleted = reservationService.deleteReservation(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}