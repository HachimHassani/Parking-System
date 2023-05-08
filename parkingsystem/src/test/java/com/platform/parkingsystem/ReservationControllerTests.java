package com.platform.parkingsystem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.platform.parkingsystem.api.controller.ReservationController;
import com.platform.parkingsystem.api.model.ParkingSpace;
import com.platform.parkingsystem.api.model.Reservation;
import com.platform.parkingsystem.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTests {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private MockMvc mockMvc;

    @Test
    public void testCreateReservation() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId("1");
        reservation.setFrom(LocalDateTime.now());
        reservation.setTo(LocalDateTime.now().plusHours(2));
        reservation.setReservedSpace(new ParkingSpace());

        when(reservationService.createReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"from\": \"2023-05-08T08:00:00\", \"to\": \"2023-05-08T10:00:00\", \"reservedSpace\": { \"id\": \"1\" } }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.from").isNotEmpty())
                .andExpect(jsonPath("$.to").isNotEmpty());
    }

    @Test
    public void testGetReservationById() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId("1");
        reservation.setFrom(LocalDateTime.now());
        reservation.setTo(LocalDateTime.now().plusHours(2));
        reservation.setReservedSpace(new ParkingSpace());

        when(reservationService.getReservationById(anyString())).thenReturn(reservation);

        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.from").isNotEmpty())
                .andExpect(jsonPath("$.to").isNotEmpty());
    }

    @Test
    public void testGetAllReservations() throws Exception {
        Reservation reservation1 = new Reservation();
        reservation1.setId("1");
        reservation1.setFrom(LocalDateTime.now());
        reservation1.setTo(LocalDateTime.now().plusHours(2));
        reservation1.setReservedSpace(new ParkingSpace());

        Reservation reservation2 = new Reservation();
        reservation2.setId("2");
        reservation2.setFrom(LocalDateTime.now());
        reservation2.setTo(LocalDateTime.now().plusHours(3));
        reservation2.setReservedSpace(new ParkingSpace());

        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        when(reservationService.getAllReservations()).thenReturn(reservations);

        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[0].from").isNotEmpty())
                .andExpect(jsonPath("$[0].to").isNotEmpty());
    }
}
