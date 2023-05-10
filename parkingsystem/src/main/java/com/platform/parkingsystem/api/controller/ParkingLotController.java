package com.platform.parkingsystem.api.controller;

import com.platform.parkingsystem.api.model.ParkingLot;
import com.platform.parkingsystem.api.model.ParkingSpace;
import com.platform.parkingsystem.api.repository.ParkingLotRepository;
import com.platform.parkingsystem.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    private final ParkingLotRepository parkingLotRepository;

    @Autowired
    public ParkingLotController(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @PostMapping
    public ResponseEntity<ParkingLot> createParkingLot(@RequestBody ParkingLot parkingLot) {
        ParkingLot createdParkingLot = parkingLotService.createParkingLot(parkingLot);
        return new ResponseEntity<>(createdParkingLot, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLot> getParkingLotById(@PathVariable String id) {
        ParkingLot parkingLot = parkingLotService.getParkingLotById(id);
        return new ResponseEntity<>(parkingLot, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingLot> updateParkingLot(@PathVariable String id, @RequestBody ParkingLot parkingLot) {
        ParkingLot updatedParkingLot = parkingLotService.updateParkingLot(id, parkingLot);
        return new ResponseEntity<>(updatedParkingLot, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable String id) {
        parkingLotService.deleteParkingLot(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/parking-spaces")
    public ResponseEntity<List<ParkingSpace>> getParkingSpacesByParkingLotId(@PathVariable String id) {
        List<ParkingSpace> parkingSpaces = parkingLotService.getParkingSpacesByParkingLotId(id);
        return new ResponseEntity<>(parkingSpaces, HttpStatus.OK);
    }

    @GetMapping(params = "city")
    public List<ParkingLot> getParkingLotsByCity(@RequestParam("city") String city) {
        return parkingLotRepository.findByCity(city);
    }

}
