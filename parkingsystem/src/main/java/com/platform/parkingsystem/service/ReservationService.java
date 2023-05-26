package com.platform.parkingsystem.service;

import com.platform.parkingsystem.api.exceptions.ResourceNotFoundException;
import com.platform.parkingsystem.api.model.ParkingLot;
import com.platform.parkingsystem.api.model.ParkingSpace;
import com.platform.parkingsystem.api.model.Reservation;
import com.platform.parkingsystem.api.model.User;
import com.platform.parkingsystem.api.repository.ReservationRepository;
import com.platform.parkingsystem.api.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;


    private UserRepository  userRepository;
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservationsForUser(User user) {
        return reservationRepository.findAllByUser(user);
    }

    public Reservation getReservationByIdForUser(String reservationId, String userId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new ResourceNotFoundException("Reservation", "id", reservationId);
        }
    }

    public Reservation updateReservationForUser(Reservation reservation, String userId) {
        Reservation existingReservation = getReservationByIdForUser(reservation.getId(), userId);
        existingReservation.setFrom(reservation.getFrom());
        existingReservation.setTo(reservation.getTo());
        existingReservation.setActive(reservation.isActive());
        return reservationRepository.save(existingReservation);
    }

    public boolean deleteReservationForUser(String reservationId, String userId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            reservationRepository.delete(reservation.get());
            return true;
        } else {
            return false;
        }
    }

    public Reservation getValidReservationByLicensePlate(String licensePlate) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findByLicensePlate(licensePlate);
        if (user.isPresent()) {
            List<Reservation> reservations = reservationRepository.findByUserAndActiveTrue(user.get());
            LocalDateTime now = LocalDateTime.now();
            for (Reservation reservation : reservations) {
                if (reservation.getTo().isAfter(now)) {
                    return reservation;
                }
            }
            throw new ResourceNotFoundException("No valid reservation found for license plate: " + licensePlate);
        }
        throw new ResourceNotFoundException("No user found with license plate: " + licensePlate);
    }

    public void checkin(Reservation reservation){
        reservation.usesParkingSpace();
    }

    public void checkout(Reservation reservation){
        reservation.freesParkingSpace();
    }

    public boolean isTimeSlotAvailable(ParkingLot parkingLot, LocalDateTime from, LocalDateTime to) {
        List<ParkingSpace> parkingSpaces = parkingLot.getParkingSpaces();
        for (ParkingSpace parkingSpace : parkingSpaces) {
            int t = 0;
            List<Reservation> reservations = reservationRepository.findByParkingSpaceAndActiveTrue(parkingSpace);
            for (Reservation reservation : reservations) {
                if (!(reservation.getTo().isAfter(from) && to.isAfter(reservation.getFrom()))) {
                    t+=1;
                }
            }
            if (t<parkingLot.getCapacity()){return true;}
        }
        return false;
    }

    public ParkingSpace chooseparkingspace(ParkingLot parkingLot, LocalDateTime from, LocalDateTime to) {
        List<ParkingSpace> parkingSpaces = parkingLot.getParkingSpaces();
        for (ParkingSpace parkingSpace : parkingSpaces) {
            boolean t  =true;
            List<Reservation> reservations = reservationRepository.findByParkingSpaceAndActiveTrue(parkingSpace);
            for (Reservation reservation : reservations) {
                if (((reservation.getTo().isAfter(from) && (reservation.getTo().isBefore(from)))||(to.isAfter(reservation.getFrom())&&to.isBefore(reservation.getFrom())))) {
                    t = false;
                    break;
                }
            }
            if (t){return parkingSpace;}
        }
        return null;
    }



}