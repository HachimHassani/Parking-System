package com.platform.parkingsystem.api.controller;

import com.platform.parkingsystem.api.model.ParkingLot;
import com.platform.parkingsystem.api.model.ParkingSpace;
import com.platform.parkingsystem.api.model.Reservation;
import com.platform.parkingsystem.api.model.User;
import com.platform.parkingsystem.api.repository.ParkingLotRepository;
import com.platform.parkingsystem.api.repository.ReservationRepository;
import com.platform.parkingsystem.api.repository.UserRepository;
import com.platform.parkingsystem.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;
    @Autowired
    private  ParkingLotRepository parkingLotRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("")
    public List<ParkingLot> getParkingLots(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userop = userRepository.findUserByEmail(username);
        if (userop.isEmpty()){
            return null;
        }
        User user = userop.get();
        List<ParkingLot> parkingLots = parkingLotService.getAllParkingLots();
        for (ParkingLot parkingLot : parkingLots) {
            boolean isFavorite = user.getFavourites() != null && user.getFavourites().contains(parkingLot);
            parkingLot.setFavorite(isFavorite);
        }
        return parkingLots;

    }

    @GetMapping(params = "city")
    public List<ParkingLot> getParkingLotsByCity(@RequestParam("city") String city) {
        return parkingLotRepository.findByCity(city);

    }

    @GetMapping(params = "name")
    public List<ParkingLot> getParkingLotsByName(@RequestParam("name") String name) {
        return parkingLotRepository.findByName(name);
    }


    @GetMapping(params = "mostPopular")
    public List<ParkingLot> getParkingLotsByPopularity() {
        return parkingLotRepository.findAll().stream()
                .sorted(Comparator.comparingInt(parkingLot -> -parkingLotService.getReservations(parkingLot.getId()).size()))
                .collect(Collectors.toList());
    }


    @GetMapping("/weekly-reservations")
    public List<Map<String, Object>> getWeeklyReservations() {

        // Create a list to hold the results
        List<Map<String, Object>> weeklyReservations = new ArrayList<>();

        // Loop through each day of the week
            for (DayOfWeek dayOfWeek : DayOfWeek.values()) {

            // Create a map to hold the results for this day of the week
            Map<String, Object> dayOfWeekReservations = new HashMap<>();
            dayOfWeekReservations.put("dayOfWeek", dayOfWeek.toString());

            // Calculate the average reservations for this day of the week for each parking lot
            List<Map<String, Object>> parkingLotReservations = new ArrayList<>();
            List<ParkingLot> parkingLots = parkingLotRepository.findAll();
            for (ParkingLot parkingLot : parkingLots) {
                int reservationsCount = 0;
                List<Reservation> reservations = parkingLotService.getReservations(parkingLot.getId());
                for (Reservation reservation : reservations) {
                    LocalDateTime fromDateTime = reservation.getFrom();
                    LocalDateTime toDateTime = reservation.getTo();
                    if (dayOfWeek.equals(fromDateTime.getDayOfWeek())) {
                        // The reservation starts on this day of the week
                        if (dayOfWeek.equals(toDateTime.getDayOfWeek())) {
                            // The reservation ends on the same day of the week, so it is counted as a single reservation
                            reservationsCount++;
                        } else {
                            // The reservation ends on a different day of the week, so it is counted as a fraction of a reservation
                            LocalDateTime endOfDay = LocalDateTime.of(fromDateTime.toLocalDate(), LocalTime.MAX);
                            Duration duration = Duration.between(fromDateTime, endOfDay);
                            double fraction = duration.getSeconds() / (24.0 * 60 * 60);
                            reservationsCount += fraction;
                        }
                    } else if (dayOfWeek.equals(toDateTime.getDayOfWeek())) {
                        // The reservation ends on this day of the week, so it is counted as a fraction of a reservation
                        LocalDateTime startOfDay = LocalDateTime.of(toDateTime.toLocalDate(), LocalTime.MIDNIGHT);
                        Duration duration = Duration.between(startOfDay, toDateTime);
                        double fraction = duration.getSeconds() / (24.0 * 60 * 60);
                        reservationsCount += fraction;
                    } else if (fromDateTime.isBefore(LocalDateTime.of(fromDateTime.toLocalDate().with(TemporalAdjusters.next(dayOfWeek)), LocalTime.MIDNIGHT))) {
                        // The reservation starts before this day of the week and ends after this day of the week, so it is counted as a full reservation
                        reservationsCount++;
                    }
                }
                Map<String, Object> parkingLotReservation = new HashMap<>();
                parkingLotReservation.put("parkingLotId", parkingLot.getId());
                parkingLotReservation.put("reservationsCount", reservationsCount);
                parkingLotReservations.add(parkingLotReservation);
            }

            // Add the results for this day of the week to the list of results
            dayOfWeekReservations.put("parkingLotReservations", parkingLotReservations);
            weeklyReservations.add(dayOfWeekReservations);
        }

        return weeklyReservations;
    }

    @GetMapping("{id}/weekly-reservations")
    public List<Map<String, Object>> getParkingLotWeeklyReservations(@PathVariable String id) {

        // Create a list to hold the results
        List<Map<String, Object>> weeklyReservations = new ArrayList<>();

        // Loop through each day of the week
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {

            // Create a map to hold the results for this day of the week
            Map<String, Object> dayOfWeekReservations = new HashMap<>();
            dayOfWeekReservations.put("dayOfWeek", dayOfWeek.toString());

            // Calculate the average reservations for this day of the week for each parking lot
            List<Map<String, Object>> parkingLotReservations = new ArrayList<>();
            List<ParkingLot> parkingLots = parkingLotRepository.findAll();

            ParkingLot parkingLot = parkingLotService.getParkingLotById(id);
                int reservationsCount = 0;
                List<Reservation> reservations = parkingLotService.getReservations(parkingLot.getId());
                for (Reservation reservation : reservations) {
                    LocalDateTime fromDateTime = reservation.getFrom();
                    LocalDateTime toDateTime = reservation.getTo();
                    if (dayOfWeek.equals(fromDateTime.getDayOfWeek())) {
                        // The reservation starts on this day of the week
                        if (dayOfWeek.equals(toDateTime.getDayOfWeek())) {
                            // The reservation ends on the same day of the week, so it is counted as a single reservation
                            reservationsCount++;
                        } else {
                            // The reservation ends on a different day of the week, so it is counted as a fraction of a reservation
                            LocalDateTime endOfDay = LocalDateTime.of(fromDateTime.toLocalDate(), LocalTime.MAX);
                            Duration duration = Duration.between(fromDateTime, endOfDay);
                            double fraction = duration.getSeconds() / (24.0 * 60 * 60);
                            reservationsCount += fraction;
                        }
                    } else if (dayOfWeek.equals(toDateTime.getDayOfWeek())) {
                        // The reservation ends on this day of the week, so it is counted as a fraction of a reservation
                        LocalDateTime startOfDay = LocalDateTime.of(toDateTime.toLocalDate(), LocalTime.MIDNIGHT);
                        Duration duration = Duration.between(startOfDay, toDateTime);
                        double fraction = duration.getSeconds() / (24.0 * 60 * 60);
                        reservationsCount += fraction;
                    } else if (fromDateTime.isBefore(LocalDateTime.of(fromDateTime.toLocalDate().with(TemporalAdjusters.next(dayOfWeek)), LocalTime.MIDNIGHT))) {
                        // The reservation starts before this day of the week and ends after this day of the week, so it is counted as a full reservation
                        reservationsCount++;
                    }
                }
                Map<String, Object> parkingLotReservation = new HashMap<>();
                parkingLotReservation.put("parkingLotId", parkingLot.getId());
                parkingLotReservation.put("reservationsCount", reservationsCount);
                parkingLotReservations.add(parkingLotReservation);


            // Add the results for this day of the week to the list of results
            dayOfWeekReservations.put("parkingLotReservations", parkingLotReservations);
            weeklyReservations.add(dayOfWeekReservations);
        }

        return weeklyReservations;
    }
    @GetMapping("/newest")
    public ResponseEntity<List<ParkingLot>> getNewestParkingLots() {
        List<ParkingLot> newestParkingLots = parkingLotService.getNewestParkingLots();
        return ResponseEntity.ok(newestParkingLots);
    }


}
