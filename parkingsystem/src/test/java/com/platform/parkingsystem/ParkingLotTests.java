package com.platform.parkingsystem;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.platform.parkingsystem.api.controller.ParkingLotController;
import com.platform.parkingsystem.api.model.ParkingLot;
import com.platform.parkingsystem.api.model.ParkingSpace;
import com.platform.parkingsystem.api.repository.ParkingLotRepository;
import com.platform.parkingsystem.service.ParkingLotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParkingLotTests {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private ParkingLotService parkingLotService;

    @InjectMocks

    private ParkingLotController parkingLotController;

    @Test
    public void createParkingLot_returnsCreatedParkingLot() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Test Parking Lot");
        parkingLot.setCity("Test City");
        ParkingLot createdParkingLot = new ParkingLot();
        createdParkingLot.setId("1");
        createdParkingLot.setName("Test Parking Lot");
        createdParkingLot.setCity("Test City");
        when(parkingLotService.createParkingLot(parkingLot)).thenReturn(createdParkingLot);

        ResponseEntity<ParkingLot> responseEntity = parkingLotController.createParkingLot(parkingLot);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdParkingLot, responseEntity.getBody());
    }

    @Test
    public void getParkingLotById_returnsParkingLot() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId("1");
        parkingLot.setName("Test Parking Lot");
        parkingLot.setCity("Test City");
        when(parkingLotService.getParkingLotById("1")).thenReturn(parkingLot);

        ResponseEntity<ParkingLot> responseEntity = parkingLotController.getParkingLotById("1");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(parkingLot, responseEntity.getBody());
    }

    @Test
    public void updateParkingLot_returnsUpdatedParkingLot() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId("1");
        parkingLot.setName("Test Parking Lot");
        parkingLot.setCity("Test City");
        when(parkingLotService.updateParkingLot("1", parkingLot)).thenReturn(parkingLot);

        ResponseEntity<ParkingLot> responseEntity = parkingLotController.updateParkingLot("1", parkingLot);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(parkingLot, responseEntity.getBody());
    }

    @Test
    public void deleteParkingLot_returnsNoContent() {
        ResponseEntity<Void> responseEntity = parkingLotController.deleteParkingLot("1");

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void getParkingSpacesByParkingLotId_returnsParkingSpaces() {
        List<ParkingSpace> parkingSpaces = Arrays.asList(new ParkingSpace(), new ParkingSpace());
        when(parkingLotService.getParkingSpacesByParkingLotId("1")).thenReturn(parkingSpaces);

        ResponseEntity<List<ParkingSpace>> responseEntity = parkingLotController.getParkingSpacesByParkingLotId("1");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(parkingSpaces, responseEntity.getBody());
    }


}
