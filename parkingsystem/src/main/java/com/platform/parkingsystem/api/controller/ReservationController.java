package com.platform.parkingsystem.api.controller;

import com.platform.parkingsystem.api.model.ParkingLot;
import com.platform.parkingsystem.api.model.ParkingSpace;
import com.platform.parkingsystem.api.model.Reservation;
import com.platform.parkingsystem.api.model.User;
import com.platform.parkingsystem.api.repository.UserRepository;
import com.platform.parkingsystem.service.ParkingLotService;
import com.platform.parkingsystem.service.ReservationService;
import com.platform.parkingsystem.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping("")
    public ResponseEntity<Reservation> createReservation(
            Authentication authentication,
            @RequestParam("parking_lot_id") String parkinglotId,
            @RequestParam("from") String from,
            @RequestParam("to") String to) {

        String username = authentication.getName();
        Optional<User> userop = userRepository.findUserByEmail(username);
        if (userop.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userop.get();

        LocalDateTime start = LocalDateTime.parse(from);
        LocalDateTime end = LocalDateTime.parse(to);

        ParkingLot parkingLot = parkingLotService.getParkingLotById(parkinglotId);
        if (parkingLot == null) {
            // Handle parking lot not found case
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Check if the time slot is available
        if (!reservationService.isTimeSlotAvailable(parkingLot, start, end)) {
            return ResponseEntity.badRequest().body(null);
        }

        ParkingSpace parkingSpace = reservationService.chooseparkingspace(parkingLot, start, end);
        // Create the reservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setParkingSpace(parkingSpace);
        reservation.setFrom(start);
        reservation.setTo(end);
        reservation.setActive(true);
        Reservation createdReservation = reservationService.createReservation(reservation);

        return ResponseEntity.ok(createdReservation);
    }

    @GetMapping()
    public ResponseEntity<List<Reservation>> getAllReservations(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userop = userRepository.findUserByEmail(username);
        if (userop.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userop.get();

        List<Reservation> reservations = reservationService.getAllReservationsForUser(user);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userop = userRepository.findUserByEmail(username);
        if (userop.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userop.get();

        Reservation reservation = reservationService.getReservationByIdForUser(id, user.getId());
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable String id, @RequestBody Reservation reservation, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userop = userRepository.findUserByEmail(username);
        if (userop.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userop.get();

        reservation.setId(id);
        Reservation updatedReservation = reservationService.updateReservationForUser(reservation, user.getId());
        if (updatedReservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userop = userRepository.findUserByEmail(username);
        if (userop.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userop.get();

        boolean deleted = reservationService.deleteReservationForUser(id, user.getId());
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
