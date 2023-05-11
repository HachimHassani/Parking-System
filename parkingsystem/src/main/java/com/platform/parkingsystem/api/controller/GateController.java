package com.platform.parkingsystem.api.controller;

import com.platform.parkingsystem.api.model.Entry;
import com.platform.parkingsystem.api.model.Exit;
import com.platform.parkingsystem.api.model.ParkingLot;
import com.platform.parkingsystem.api.model.Reservation;
import com.platform.parkingsystem.service.ParkingLotService;
import com.platform.parkingsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gate")
public class GateController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping("/entry")
    public ResponseEntity<String> entry(@RequestBody Entry entry) {
        String licensePlate = entry.getLicensePlate();
        ParkingLot parkingLot = entry.getParkingLot();

        if (parkingLot == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking lot not found");
        }

        Reservation reservation = reservationService.getValidReservationByLicensePlate(licensePlate);

        if (reservation == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No valid reservation found");
        }
        reservation.usesParkingSpace();
        boolean grantedAccess = parkingLot.grantAccess();

        if (!grantedAccess) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access not granted");
        }

        return ResponseEntity.ok("Access granted");
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exit(@RequestBody Exit exit) {
        String licensePlate = exit.getLicensePlate();
        ParkingLot parkingLot = exit.getParkingLot();

        if (parkingLot == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking lot not found");
        }
        Reservation reservation = reservationService.getValidReservationByLicensePlate(licensePlate);
        reservation.freesParkingSpace();
        boolean exitSuccess = parkingLot.releaseSpace();

        if (!exitSuccess) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exit not successful");
        }

        return ResponseEntity.ok("Exit successful");
    }
}
