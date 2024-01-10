package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.BarbeariaDTO;
import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.models.Horario;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.deizon.system_barbershop.domain.repositories.BarbeariaRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.BarbeariaDTOMapper;
import com.deizon.system_barbershop.domain.services.exceptions.ExistingFieldException;
import com.deizon.system_barbershop.domain.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BarbeariaServiceTest {

    public static final UUID ID = UUID.randomUUID();
    public static final String NOME = "Dudu Cortas";
    public static final String CNPJ = "96852528000168";
    public static final Horario HORARIO = new Horario(UUID.randomUUID(), Instant.now(), Instant.now().plusSeconds(1260L), new Barbearia(), new Reserva());
    public static final Integer INDEX = 0;

    @InjectMocks
    private BarbeariaService service;
    @Mock
    private BarbeariaRepository repository;
    @Mock
    private BarbeariaDTOMapper mapper;

    private Barbearia barbearia;
    private BarbeariaDTO barbeariaDTO;
    private Optional<Barbearia> optional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startBarbearia();
    }

    @Test
    void whenFindAllThenReturnListOfBarbeariaDTO() {
        when(repository.findAll()).thenReturn(List.of(barbearia));
        when(mapper.apply(any())).thenReturn(barbeariaDTO);

        List<BarbeariaDTO> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(BarbeariaDTO.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NOME, response.get(INDEX).getNome());
        assertEquals(CNPJ, response.get(INDEX).getCnpj());
        assertEquals(HORARIO, response.get(INDEX).getHorarios().get(INDEX));
    }

    @Test
    void whenFindByIDThenReturnBarbeariaDTO() {
        when(repository.findById(any(UUID.class))).thenReturn(optional);
        when(mapper.apply(barbearia)).thenReturn(barbeariaDTO);

        BarbeariaDTO response = service.findByID(ID);

        assertNotNull(response);
        assertEquals(BarbeariaDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(CNPJ, response.getCnpj());
        assertEquals(HORARIO, response.getHorarios().get(INDEX));
    }

    @Test
    void whenFindByIDThenReturnResourceNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        try {
            BarbeariaDTO response = service.findByID(ID);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O recurso com o ID: %s não foi encontrado", ID.toString()), e.getMessage());
        }
    }

    @Test
    void whenAddResourceThenReturnBarbearia() {
        when(repository.save(any())).thenReturn(barbearia);

        Barbearia response = service.addResource(barbeariaDTO);

        assertNotNull(response);
        assertEquals(Barbearia.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(CNPJ, response.getCnpj());
        assertEquals(HORARIO, response.getHorarios().get(INDEX));
    }

    @Test
    void whenAddResourceThenExistingNomeException() {
        when(repository.findFirstByNome(anyString())).thenReturn(optional);
        when(repository.save(any())).thenReturn(barbearia);

        try {
            barbeariaDTO.setId(UUID.randomUUID());
            barbeariaDTO.setCnpj("32898944000114");
            Barbearia response = service.addResource(barbeariaDTO);
        } catch (Exception e) {
            assertEquals(ExistingFieldException.class, e.getClass());
            assertEquals("Nome já vinculado a outra barbeária", e.getMessage());
        }
    }

    @Test
    void whenAddResourceThenExistingCnpjException() {
        when(repository.findFirstByCnpj(anyString())).thenReturn(optional);
        when(repository.save(any())).thenReturn(barbearia);

        try {
            barbeariaDTO.setId(UUID.randomUUID());
            barbeariaDTO.setNome("Teste Teste");
            Barbearia response = service.addResource(barbeariaDTO);
        } catch (Exception e) {
            assertEquals(ExistingFieldException.class, e.getClass());
            assertEquals("CNPJ já vinculado a outra barbeária", e.getMessage());
        }
    }

    @Test
    void whenRemResourceTheReturnVoid() {
        when(repository.findById(any(UUID.class))).thenReturn(optional);
        doNothing().when(repository).deleteById(any(UUID.class));
        service.remResource(ID);
        verify(repository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void whenRemResourceThenReturnRescourceNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        try {
            service.remResource(ID);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O recurso com o ID: %s não foi encontrado", ID.toString()), e.getMessage());
        }
    }

    @Test
    void whenUpdateResourceThenReturnBarbearia() {
        when(repository.getReferenceById(any(UUID.class))).thenReturn(barbearia);
        when(repository.save(any())).thenReturn(barbearia);

        Barbearia response = service.updateResource(ID, barbeariaDTO);

        assertNotNull(response);

        assertEquals(Barbearia.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(CNPJ, response.getCnpj());
        assertEquals(HORARIO, response.getHorarios().get(INDEX));
    }

    @Test
    void whenUpdateResourceThenReturnExistingNomeException() {
        when(repository.findFirstByNome(anyString())).thenReturn(optional);
        when(repository.getReferenceById(any(UUID.class))).thenReturn(barbearia);

        try {
            optional.get().setId(UUID.randomUUID());
            optional.get().setCnpj("60175176000106");
            Barbearia response = service.updateResource(ID, barbeariaDTO);
        } catch (Exception e) {
            assertEquals(ExistingFieldException.class, e.getClass());
            assertEquals("Nome já vinculado a outra barbeária", e.getMessage());
        }

    }

    @Test
    void whenUpdateResourceThenReturnExistingCnpjEception() {
        when(repository.findFirstByCnpj(anyString())).thenReturn(optional);
        when(repository.getReferenceById(any(UUID.class))).thenReturn(barbearia);

        try {
            optional.get().setId(UUID.randomUUID());
            optional.get().setNome("Teste teste");
            Barbearia response = service.updateResource(ID, barbeariaDTO);
        } catch (Exception e) {
            assertEquals(ExistingFieldException.class, e.getClass());
            assertEquals("CNPJ já vinculado a outra barbeária", e.getMessage());
        }
    }

    private void startBarbearia() {
        barbearia = new Barbearia(ID
                ,NOME
                ,CNPJ
                ,List.of(HORARIO));
        barbeariaDTO = new BarbeariaDTO(ID
                ,NOME
                ,CNPJ
                ,List.of(HORARIO));
        optional = Optional.of(new Barbearia(ID
                ,NOME
                ,CNPJ
                ,List.of(HORARIO)));
    }
}