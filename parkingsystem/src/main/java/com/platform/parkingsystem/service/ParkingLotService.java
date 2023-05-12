package com.platform.parkingsystem.service;

import com.platform.parkingsystem.api.exceptions.ResourceNotFoundException;
import com.platform.parkingsystem.api.model.ParkingLot;
import com.platform.parkingsystem.api.model.ParkingSpace;
import com.platform.parkingsystem.api.model.Reservation;
import com.platform.parkingsystem.api.repository.ParkingLotRepository;
import com.platform.parkingsystem.api.repository.ParkingSpaceRepository;
import com.platform.parkingsystem.api.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public ParkingLot createParkingLot(ParkingLot parkingLot) {
        int capacity = parkingLot.getCapacity();
        List<ParkingSpace> parkingSpaces = new ArrayList<>();
        for (int i = 1; i <= capacity; i++) {
            String spaceName = "Space " + i;
            ParkingSpace parkingSpace = new ParkingSpace(spaceName);
            parkingSpaces.add(parkingSpace);
        }
        parkingLot.setParkingSpaces(parkingSpaces);
        return parkingLotRepository.save(parkingLot);
    }

    public ParkingLot getParkingLotById(String id) {
        return parkingLotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ParkingLot", "id", id));
    }

    public ParkingLot updateParkingLot(String id, ParkingLot parkingLot) {
        ParkingLot existingParkingLot = getParkingLotById(id);
        existingParkingLot.setName(parkingLot.getName());
        existingParkingLot.setParkingFee(parkingLot.getParkingFee());
        /*existingParkingLot.setTotalSpaces(parkingLot.getTotalSpaces());
        existingParkingLot.setAvailableSpaces(parkingLot.getAvailableSpaces());
        existingParkingLot.setParkingSpaces(parkingLot.getParkingSpaces());*/
        return parkingLotRepository.save(existingParkingLot);
    }

    public void deleteParkingLot(String id) {
        parkingLotRepository.deleteById(id);
    }

    public List<ParkingSpace> getParkingSpacesByParkingLotId(String parkingLotId) {
        return parkingSpaceRepository.findByParkingLotId(parkingLotId);
    }



    public List<Reservation> getReservations(String id) {
        List<Reservation> allReservations = new ArrayList<>();
        for (ParkingSpace space : getParkingSpacesByParkingLotId(id)) {
            allReservations.addAll(reservationRepository.findByParkingSpace(space));
        }
        return allReservations;
    }
}
