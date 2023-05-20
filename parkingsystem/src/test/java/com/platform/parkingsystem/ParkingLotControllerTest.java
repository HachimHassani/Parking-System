package com.platform.parkingsystem;

import com.platform.parkingsystem.api.controller.ParkingLotController;
import com.platform.parkingsystem.api.controller.response.ParkingLotResponse;
import com.platform.parkingsystem.api.model.ParkingLot;
import com.platform.parkingsystem.api.model.ParkingSpace;
import com.platform.parkingsystem.api.repository.ParkingLotRepository;
import com.platform.parkingsystem.api.repository.ReservationRepository;
import com.platform.parkingsystem.service.ParkingLotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ParkingLotControllerTest {

    @Mock
    private ParkingLotService parkingLotService;
    @Mock
    private ParkingLotRepository parkingLotRepository;
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ParkingLotController parkingLotController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateParkingLot() {
        ParkingLot parkingLot = new ParkingLot("Sample Parking Lot", 100, 10.0, "City");
        ParkingLot createdParkingLot = new ParkingLot();
        when(parkingLotService.createParkingLot(parkingLot)).thenReturn(createdParkingLot);

        ResponseEntity<ParkingLot> response = parkingLotController.createParkingLot(parkingLot);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdParkingLot, response.getBody());
        verify(parkingLotService, times(1)).createParkingLot(parkingLot);
    }

    @Test
    void testGetParkingLotById() {
        String id = "123";
        ParkingLot parkingLot = new ParkingLot("Sample Parking Lot", 100, 10.0, "City");

        when(parkingLotService.getParkingLotById(id)).thenReturn(parkingLot);

        ResponseEntity<ParkingLot> response = parkingLotController.getParkingLotById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(parkingLot, response.getBody());
        verify(parkingLotService, times(1)).getParkingLotById(id);
    }

    // Write similar tests for other methods in the ParkingLotController class

    @Test
    void testGetParkingSpacesByParkingLotId() {
        String id = "123";
        List<ParkingSpace> parkingSpaces = new ArrayList<>();
        when(parkingLotService.getParkingSpacesByParkingLotId(id)).thenReturn(parkingSpaces);

        ResponseEntity<List<ParkingSpace>> response = parkingLotController.getParkingSpacesByParkingLotId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(parkingSpaces, response.getBody());
        verify(parkingLotService, times(1)).getParkingSpacesByParkingLotId(id);
    }

    @Test
    void testGetParkingLotsByCity() {
        String city = "New York";
        List<ParkingLot> parkingLots = new ArrayList<>();
        when(parkingLotRepository.findByCity(city)).thenReturn(parkingLots);

        List<ParkingLotResponse> result = parkingLotController.getParkingLotsByCity(city);

        assertEquals(parkingLots, result);
        verify(parkingLotRepository, times(1)).findByCity(city);
    }

    @Test
    void testGetParkingLotsByName() {
        String name = "Parking Lot A";
        List<ParkingLot> parkingLots = new ArrayList<>();
        when(parkingLotRepository.findByName(name)).thenReturn(parkingLots);

        List<ParkingLot> result = parkingLotController.getParkingLotsByName(name);

        assertEquals(parkingLots, result);
        verify(parkingLotRepository, times(1)).findByName(name);
    }

    @Test
    void testGetParkingLotsByPopularity() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        when(parkingLotRepository.findAll()).thenReturn(parkingLots);

        List<ParkingLot> result = parkingLotController.getParkingLotsByPopularity();

        assertEquals(parkingLots, result);
        verify(parkingLotRepository, times(1)).findAll();
    }

    @Test
    void testGetWeeklyReservations() {
        // Mocking the necessary dependencies and setting up the test data is required for this test
        // Test the behavior and assertions based on the logic in the getWeeklyReservations() method
        // Mocking and asserting specific behavior of parkingLotService and parkingLotRepository might be necessary
    }
}