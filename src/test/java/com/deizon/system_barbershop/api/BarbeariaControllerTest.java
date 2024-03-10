package com.deizon.system_barbershop.api;

import com.deizon.system_barbershop.api.controllers.BarbeariaController;
import com.deizon.system_barbershop.domain.dtos.BarbeariaDTO;
import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.models.Horario;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.deizon.system_barbershop.domain.services.BarbeariaService;
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
class BarbeariaControllerTest {

    /*
    public static final UUID ID = UUID.randomUUID();
    public static final String NOME = "Dudu Cortas";
    public static final Horario HORARIO = new Horario(UUID.randomUUID(), Instant.now(), Instant.now().plusSeconds(1260L), new Barbearia(), new Reserva());
    public static final Integer INDEX = 0;

    @InjectMocks
    private BarbeariaController controller;
    @Mock
    private BarbeariaService service;

    private Barbearia barbearia;
    private BarbeariaDTO barbeariaDTO;
    private Optional<Barbearia> optional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startBarbearia();
    }

    @Test
    void whenGetBarbeariasThenReturnOk() {
        when(service.findAll()).thenReturn(List.of(barbeariaDTO));

        ResponseEntity<List<BarbeariaDTO>> response = controller.getBarbearias();

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(BarbeariaDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID,response.getBody().get(INDEX).getId());
        assertEquals(NOME,response.getBody().get(INDEX).getNome());
        assertEquals(HORARIO, response.getBody().get(INDEX).getHorarios().get(INDEX));
    }

    @Test
    void whenGetBarbeariaThenReturnOk() {
        when(service.findByID(any(UUID.class))).thenReturn(barbeariaDTO);

        ResponseEntity<BarbeariaDTO> response = controller.getBarbearia(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(BarbeariaDTO.class, response.getBody().getClass());

        assertEquals(ID,response.getBody().getId());
        assertEquals(NOME,response.getBody().getNome());
        assertEquals(HORARIO, response.getBody().getHorarios().get(INDEX));
    }

    @Test
    void whenCreateBarbeariaThenReturnCreated() {
        when(service.addResource(any())).thenReturn(barbearia);

        ResponseEntity<Barbearia> response = controller.createBarbearia(barbeariaDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Barbearia.class, response.getBody().getClass());

        assertEquals(ID,response.getBody().getId());
        assertEquals(NOME,response.getBody().getNome());
        assertEquals(HORARIO, response.getBody().getHorarios().get(INDEX));
    }

    @Test
    void whenDeleteBarbeariaThenReturnNoContent() {
        doNothing().when(service).remResource(any(UUID.class));

        ResponseEntity<?> response = controller.deleteBarbearia(ID);

        assertNotNull(response);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());

        verify(service, times(1)).remResource(any(UUID.class));
    }

    @Test
    void whenUpdateBarbeariaThenReturnOk() {
        when(service.updateResource(any(UUID.class), any())).thenReturn(barbearia);

        ResponseEntity<Barbearia> response = controller.updateBarbearia(ID, barbeariaDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Barbearia.class, response.getBody().getClass());

        assertEquals(ID,response.getBody().getId());
        assertEquals(NOME,response.getBody().getNome());
        assertEquals(HORARIO, response.getBody().getHorarios().get(INDEX));
    }

    private void startBarbearia() {
        barbearia = new Barbearia(ID
                ,NOME
                ,List.of(HORARIO));
        barbeariaDTO = new BarbeariaDTO(ID
                ,NOME
                ,List.of(HORARIO));
        optional = Optional.of(new Barbearia(ID
                ,NOME
                ,List.of(HORARIO)));

    }

     */
}