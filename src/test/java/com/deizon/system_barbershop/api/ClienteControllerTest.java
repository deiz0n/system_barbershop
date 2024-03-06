package com.deizon.system_barbershop.api;

import com.deizon.system_barbershop.api.controllers.ClienteController;
import com.deizon.system_barbershop.domain.dtos.ClienteDTO;
import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.models.Horario;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.deizon.system_barbershop.domain.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteControllerTest {

    public static final int INDEX = 0;
    @InjectMocks
    private ClienteController controller;
    @Mock
    private ClienteService service;

    private Cliente cliente;
    private ClienteDTO clienteDTO;
    private Optional<Cliente> optional;

    public static final UUID ID = UUID.randomUUID();
    public static final String NOME = "Carlos Eduardo";
    public static final String CPF = "14595214049";
    public static final String TELEFONE = "11940028922";
    public static final String EMAIL = "eduardo@gmail.com";
    public static final Reserva RESERVA = new Reserva(UUID.randomUUID(), new Cliente(), new Horario());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCliente();
    }

    @Test
    void whenGetClientesThenReturnOk() {
        when(service.findAll()).thenReturn(List.of(clienteDTO));

        ResponseEntity<List<ClienteDTO>> response = controller.getClientes();

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ClienteDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NOME, response.getBody().get(INDEX).getNome());
        assertEquals(CPF, response.getBody().get(INDEX).getCpf());
        assertEquals(TELEFONE, response.getBody().get(INDEX).getTelefone());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(RESERVA, response.getBody().get(INDEX).getReservas().get(INDEX));
    }

    @Test
    void whenGetClienteThenReturnOk() {
        when(service.findByID(any(UUID.class))).thenReturn(clienteDTO);

        ResponseEntity<ClienteDTO> response = controller.getCliente(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ClienteDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
        assertEquals(CPF, response.getBody().getCpf());
        assertEquals(TELEFONE, response.getBody().getTelefone());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(RESERVA, response.getBody().getReservas().get(INDEX));
    }

    @Test
    void whenCreateClienteThenReturnCreated() {
        when(service.addResource(any())).thenReturn(cliente);

        ResponseEntity<ClienteDTO> response = controller.createCliente(clienteDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ClienteDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
        assertEquals(CPF, response.getBody().getCpf());
        assertEquals(TELEFONE, response.getBody().getTelefone());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(RESERVA, response.getBody().getReservas().get(INDEX));
    }

    @Test
    void whenDeleteClienteThenReturnNoContent() {
        doNothing().when(service).remResource(any(UUID.class));

        ResponseEntity<?> response = controller.deleteCliente(ID);

        assertNotNull(response);
        assertNull(response.getBody());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(service, times(1)).remResource(ID);
    }

    @Test
    void whenUpdateClienteThenReturnOk() {
        when(service.updateResource(any(UUID.class), any())).thenReturn(cliente);

        ResponseEntity<Cliente> response = controller.updateCliente(ID, clienteDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Cliente.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
        assertEquals(CPF, response.getBody().getCpf());
        assertEquals(TELEFONE, response.getBody().getTelefone());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(RESERVA, response.getBody().getReservas().get(INDEX));
    }

    private void startCliente() {
        cliente = new Cliente(ID
                ,NOME
                ,CPF
                ,TELEFONE
                ,EMAIL
                , List.of(RESERVA));
        clienteDTO = new ClienteDTO(ID
                ,NOME
                ,CPF
                ,TELEFONE
                ,EMAIL
                ,List.of(RESERVA));
        optional = Optional.of(new Cliente(ID
                ,NOME
                ,CPF
                ,TELEFONE
                ,EMAIL
                ,List.of(RESERVA)));
    }
}