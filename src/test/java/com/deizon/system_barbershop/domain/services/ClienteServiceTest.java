package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.ClienteDTO;
import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.models.Horario;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.deizon.system_barbershop.domain.repositories.ClienteRepository;
import com.deizon.system_barbershop.domain.repositories.ReservaRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.ClienteDTOMapper;
import com.deizon.system_barbershop.domain.services.exceptions.DataIntegrityException;
import com.deizon.system_barbershop.domain.services.exceptions.ExistingFieldException;
import com.deizon.system_barbershop.domain.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteServiceTest {

    public static final UUID ID = UUID.randomUUID();
    public static final String NOME = "Carlos Eduardo";
    public static final String TELEFONE = "11940028922";
    public static final String EMAIL = "eduardo@gmail.com";
    public static final Reserva RESERVA = new Reserva(UUID.randomUUID(), new Cliente(), new Horario());
    public static final Integer INDEX = 0;

    @InjectMocks
    private ClienteService service;
    @Mock
    private ClienteRepository repository;
    @Mock
    private ClienteDTOMapper mapper;
    @Mock
    private ReservaRepository reservaRepository;

    private Cliente cliente;
    private ClienteDTO clienteDTO;
    private Optional<Cliente> optional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCliente();
    }

    @Test
    void whenFindAllThenReturnListOfClienteDTO() {
        ArrayList<Cliente> arrayList = new ArrayList<>();
        arrayList.add(INDEX, cliente);

        when(repository.findAll()).thenReturn(arrayList);
        when(mapper.apply(any())).thenReturn(clienteDTO);

        List<ClienteDTO> response = service.findAll();

        assertNotNull(response);

        assertEquals(ArrayList.class, response.getClass());
        assertEquals(ClienteDTO.class, response.get(INDEX).getClass());
        assertEquals(1, response.size());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NOME, response.get(INDEX).getNome());
        assertEquals(TELEFONE, response.get(INDEX).getTelefone());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(RESERVA, response.get(INDEX).getReservas().get(INDEX));
    }

    @Test
    void whenFindByIDThenReturnClienteDTO() {
        when(repository.findById(any(UUID.class))).thenReturn(optional);
        when(mapper.apply(any())).thenReturn(clienteDTO);

        ClienteDTO response = service.findByID(ID);

        assertNotNull(response);

        assertEquals(ClienteDTO.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(TELEFONE, response.getTelefone());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(RESERVA, response.getReservas().get(INDEX));
    }

    @Test
    void whenFindByIDThenReturnResourceNotFoundException() {
        when(repository.findById(any(UUID.class))).thenThrow(new ResourceNotFoundException(ID));

        try {
            ClienteDTO response = service.findByID(ID);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O recurso com o ID: %s não foi encontrado", ID.toString()), e.getMessage());
        }
    }

    @Test
    void whenAddResourceThenReturnCliente() {
        when(reservaRepository.findFirstByCliente(any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(cliente);

        Cliente response = service.addResource(clienteDTO);

        assertNotNull(response);

        assertEquals(Cliente.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(TELEFONE, response.getTelefone());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(RESERVA, response.getReservas().get(INDEX));
    }

    @Test
    void whenAddResourceThenReturnExistingEmailException() {
        when(reservaRepository.findFirstByCliente(any())).thenReturn(Optional.empty());
        when(repository.findFirstByEmail(anyString())).thenReturn(optional);

        try {
            optional.get().setId(UUID.randomUUID());
            optional.get().setTelefone("telefone teste");
            Cliente response = service.addResource(clienteDTO);
        } catch (Exception e) {
            assertEquals(ExistingFieldException.class, e.getClass());
            assertEquals("Email já cadastrado", e.getMessage());
        }
    }

    @Test
    void whenAddResourceThenReturnExistingTelefoneException() {
        when(reservaRepository.findFirstByCliente(any())).thenReturn(Optional.empty());
        when(repository.findFirstByTelefone(anyString())).thenReturn(optional);

        try {
            optional.get().setId(UUID.randomUUID());
            optional.get().setEmail("teste@gmail.com");
            Cliente response = service.addResource(clienteDTO);
        } catch (Exception e) {
            assertEquals(ExistingFieldException.class, e.getClass());
            assertEquals("Telefone já cadastrado", e.getMessage());
        }
    }

    @Test
    void whenRemResourceThenReturnVoid() {
        when(reservaRepository.findFirstByCliente(any())).thenReturn(Optional.empty());
        when(repository.findById(any(UUID.class))).thenReturn(optional);

        doNothing().when(repository).deleteById(ID);

        service.remResource(ID);

        verify(repository, times(1)).deleteById(ID);
    }

    @Test
    void whenRemResourceThenReturnResourceNotFoundException() {
        when(reservaRepository.findFirstByCliente(any())).thenReturn(Optional.empty());
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        try {
            service.remResource(ID);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O recurso com o ID: %s não foi encontrado", ID.toString()), e.getMessage());
        }

    }

    @Test
    void whenRemResourceThenReturnDataIntegrityException() {
        when(reservaRepository.findFirstByCliente(any())).thenReturn(Optional.of(new Reserva(UUID.randomUUID(),new Cliente(), new Horario())));
        when(repository.findById(any(UUID.class))).thenReturn(optional);

        try {
            service.remResource(ID);
        } catch (Exception e) {
            assertEquals(DataIntegrityException.class, e.getClass());
            assertEquals("O cliente não pode ser excluído pois está vinculado a uma reserva.", e.getMessage());
        }
    }

    @Test
    void whenUpdateResourceThenReturnCliente() {
        when(repository.getReferenceById(any(UUID.class))).thenReturn(cliente);
        when(repository.save(any())).thenReturn(cliente);

        Cliente response = service.updateResource(ID, clienteDTO);

        assertNotNull(response);

        assertEquals(Cliente.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(TELEFONE, response.getTelefone());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(RESERVA, response.getReservas().get(INDEX));
    }

    @Test
    void whenUpdateResourceThenReturnResourceNotFoundException() {
        when(repository.getReferenceById(any(UUID.class))).thenThrow(new ResourceNotFoundException(ID));

        try {
            Cliente response = service.updateResource(ID, clienteDTO);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O recurso com o ID: %s não foi encontrado", ID.toString()), e.getMessage());
            assertThrows(ResourceNotFoundException.class, () -> service.updateResource(ID, clienteDTO));
        }
    }

    @Test
    void whenUpdateResourceThenReturnExistingEmailException() {
        when(repository.getReferenceById(any(UUID.class))).thenReturn(cliente);
        when(repository.findFirstByEmail(anyString())).thenReturn(optional);

        try {
            optional.get().setId(UUID.randomUUID());
            optional.get().setTelefone("telefone teste");
            Cliente response = service.updateResource(ID, clienteDTO);
        } catch (Exception e) {
            assertEquals(ExistingFieldException.class, e.getClass());
            assertEquals("Email já cadastrado", e.getMessage());
        }
    }

    @Test
    void whenUpdateResourceThenReturnExistingTelefoneException() {
        when(repository.getReferenceById(any(UUID.class))).thenReturn(cliente);
        when(repository.findFirstByTelefone(anyString())).thenReturn(optional);

        try {
            optional.get().setId(UUID.randomUUID());
            optional.get().setEmail("teste@gmail.com");
            Cliente response = service.updateResource(ID, clienteDTO);
        } catch (Exception e) {
            assertEquals(ExistingFieldException.class, e.getClass());
            assertEquals("Telefone já cadastrado", e.getMessage());
        }
    }

    private void startCliente() {
        cliente = new Cliente(ID
                ,NOME
                ,TELEFONE
                ,EMAIL
                ,List.of(RESERVA));
        clienteDTO = new ClienteDTO(ID
                ,NOME
                ,TELEFONE
                ,EMAIL
                ,List.of(RESERVA));
        optional = Optional.of(new Cliente(ID
                ,NOME
                ,TELEFONE
                ,EMAIL
                ,List.of(RESERVA)));
    }

}