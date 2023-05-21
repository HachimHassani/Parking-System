/*
package com.platform.parkingsystem;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.parkingsystem.api.controller.ReservationController;
import com.platform.parkingsystem.api.model.ParkingLot;
import com.platform.parkingsystem.api.model.ParkingSpace;
import com.platform.parkingsystem.api.model.Reservation;
import com.platform.parkingsystem.api.model.User;
import com.platform.parkingsystem.service.ParkingLotService;
import com.platform.parkingsystem.service.ReservationService;
import com.platform.parkingsystem.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private UserService userService;

    @MockBean
    private ParkingLotService parkingLotService;

    @MockBean
    private ReservationController reservationController;
    private Reservation reservation;
    private User user;
    private ParkingLot parkingLot;
    private ParkingSpace parkingSpace;


    public  void setup() {
        user = new User();
        user.setId("1");
        user.setEmail("testUser");
        user.setPassword("password");

        parkingLot = new ParkingLot();
        parkingLot.setId("1");
        parkingLot.setName("testParkingLot");
        parkingLot.setCity("rabat");
        parkingLot.setCapacity(10);

        parkingSpace = new ParkingSpace();
        parkingSpace.setId("1");
        parkingSpace.setParkingLot(parkingLot);

        reservation = new Reservation();
        reservation.setId("1");
        reservation.setUser(user);
        reservation.setParkingSpace(parkingSpace);
        reservation.setFrom(LocalDateTime.now());
        reservation.setTo(LocalDateTime.now().plusHours(1));
        reservation.setActive(true);
    }

    @Test
    public void createReservation_ReturnsCreatedReservation_WhenValidRequest() throws Exception {
        // Arrange

        when(userService.getUserById("1")).thenReturn(user);
        when(parkingLotService.getParkingLotById("1")).thenReturn(parkingLot);
        when(reservationService.isTimeSlotAvailable(parkingLot, reservation.getFrom(), reservation.getTo())).thenReturn(true);
        when(reservationService.chooseparkingspace(parkingLot, reservation.getFrom(), reservation.getTo())).thenReturn(parkingSpace);
        when(reservationService.createReservation(reservation)).thenReturn(reservation);

        // Act
        MvcResult result = mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reservation))
                        .param("user_id", "1")
                        .param("parking_lot_id", "1")
                        .param("from", reservation.getFrom().toString())
                        .param("to", reservation.getTo().toString()))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        verify(userService, times(1)).getUserById("1");
        verify(parkingLotService, times(1)).getParkingLotById("1");
        verify(reservationService, times(1)).isTimeSlotAvailable(parkingLot, reservation.getFrom(), reservation.getTo());
        verify(reservationService, times(1)).chooseparkingspace(parkingLot, reservation.getFrom(), reservation.getTo());
        verify(reservationService, times(1)).createReservation(reservation);

        Reservation createdReservation = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Reservation.class);
        assertEquals(reservation.getId(), createdReservation.getId());
        assertEquals(reservation.getUser(), createdReservation.getUser());
        assertEquals(reservation.getParkingSpace(), createdReservation.getParkingSpace());
        assertEquals(reservation.getFrom(), createdReservation.getFrom());
        assertEquals(reservation.getTo(), createdReservation.getTo());
        assertTrue(createdReservation.isActive());
    }

    @Test
    public void createReservation_ReturnsBadRequest_WhenTimeSlotUnavailable() {
        // Arrange

        User user = new User();
        user.setId("user1");
        user.setEmail("testUser");

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId("lot1");
        parkingLot.setName("Test Lot");

        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId("space1");
        parkingSpace.setSpaceNumber("1A");
        parkingSpace.setParkingLot(parkingLot);

        LocalDateTime start = LocalDateTime.of(2022, 6, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2022, 6, 1, 12, 0);

        Reservation reservation = new Reservation();
        reservation.setFrom(start);
        reservation.setTo(end);

        given(userService.getUserById("user1")).willReturn(user);
        given(parkingLotService.getParkingLotById("lot1")).willReturn(parkingLot);
        given(reservationService.isTimeSlotAvailable(parkingLot, start, end)).willReturn(false);

        // Act
        ResponseEntity<Reservation> response = reservationController.createReservation(reservation, "user1", "lot1", start.toString(), end.toString());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
*/