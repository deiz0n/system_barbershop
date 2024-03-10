package com.deizon.system_barbershop.api;

import com.deizon.system_barbershop.api.controllers.ReservaController;
import com.deizon.system_barbershop.domain.dtos.ReservaDTO;
import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.models.Horario;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.deizon.system_barbershop.domain.services.EmailService;
import com.deizon.system_barbershop.domain.services.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReservaControllerTest {

    /*
    public static final Integer INDEX = 0;
    public static final UUID ID = UUID.randomUUID();
    public static final Cliente CLIENTE = new Cliente(ID, "Eduardo", "94027732073", "1140028922", "teste@gmail.com", List.of());
    public static final Horario HORARIO = new Horario(ID, Instant.now(), Instant.now().plusSeconds(2160L), new Barbearia(), new Reserva());


    @InjectMocks
    private ReservaController controller;
    @Mock
    private ReservaService service;
    @Mock
    private EmailService emailService;

    private Reserva reserva;
    private ReservaDTO reservaDTO;
    private Optional<Reserva> optional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startReserva();
    }

    @Test
    void whenGetReservasThenReturnOk() {
        when(service.findAll()).thenReturn(List.of(reservaDTO));

        ResponseEntity<List<ReservaDTO>> response = controller.getReservas();

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReservaDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(CLIENTE, response.getBody().get(INDEX).getCliente());
        assertEquals(HORARIO, response.getBody().get(INDEX).getHorario());
    }

    @Test
    void whenGetReservaThenReturnOk() {
        when(service.findByID(any(UUID.class))).thenReturn(reservaDTO);

        ResponseEntity<ReservaDTO> response = controller.getReserva(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReservaDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(CLIENTE, response.getBody().getCliente());
        assertEquals(HORARIO, response.getBody().getHorario());
    }

    @Test
    void whenCreateReservaThenReturnCreated() {
        when(service.addResource(any())).thenReturn(reserva);
        doNothing().when(emailService).sendEmail(any(UUID.class), any());

        ResponseEntity<?> response = controller.createReserva(reservaDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Reserva.class, response.getBody().getClass());
    }

    @Test
    void whenDeleteReservaThenReturnNoContent() {
        doNothing().when(service).remResource(any(UUID.class));

        ResponseEntity<?> response = controller.deleteReserva(ID);

        assertNotNull(response);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(service, times(1)).remResource(ID);
    }

    @Test
    void whenUpdateReservaThenReturnOk() {
        when(service.updateResource(any(UUID.class), any())).thenReturn(reserva);

        ResponseEntity<Reserva> response = controller.updateReserva(ID, reservaDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Reserva.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(CLIENTE, response.getBody().getCliente());
        assertEquals(HORARIO, response.getBody().getHorario());
    }

    private void startReserva() {
        reserva = new Reserva(ID, CLIENTE, HORARIO);
        reservaDTO = new ReservaDTO(ID, CLIENTE, HORARIO);
        optional = Optional.of(new Reserva(ID, CLIENTE, HORARIO));
    }

     */
}