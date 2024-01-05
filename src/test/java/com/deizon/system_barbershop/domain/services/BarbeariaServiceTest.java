package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.BarbeariaDTO;
import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.repositories.BarbeariaRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.BarbeariaDTOMapper;
import com.deizon.system_barbershop.domain.services.exceptions.ExistingFieldException;
import com.deizon.system_barbershop.domain.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BarbeariaServiceTest {

    public static final UUID ID = UUID.fromString("261254e5-6683-447c-9c2a-43e153339773");
    public static final String NOME = "barbearia 1";
    public static final String CNPJ = "84623997000126";
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
    }

    @Test
    void whenFindByIDThenReturnResourceNotFoundException() {
        when(repository.findById(any(UUID.class))).thenThrow(new ResourceNotFoundException(ID));

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
    }

    @Test
    void whenAddResourceThenExistingNomeException() {
        when(repository.findByNome(anyString())).thenReturn(optional);
        when(repository.save(any())).thenReturn(barbearia);

        try {
            barbearia.setId(UUID.randomUUID());
            barbearia.setCnpj("32898944000114");
            service.dataValidation(barbearia);
            Barbearia response = service.addResource(barbeariaDTO);
        } catch (Exception e) {
            assertEquals(ExistingFieldException.class, e.getClass());
            assertEquals("Nome já vinculado a outra barbeária", e.getMessage());
        }
    }

    @Test
    void whenAddResourceThenExistingCnpjException() {
        when(repository.findByCnpj(anyString())).thenReturn(optional);
        when(repository.save(any())).thenReturn(barbearia);

        try {
            barbearia.setId(UUID.randomUUID());
            barbearia.setNome("Teste Teste");
            service.dataValidation(barbearia);
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
        when(repository.findById(any(UUID.class))).thenThrow(new ResourceNotFoundException(ID));

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
    }

    @Test
    void whenUpdateResourceThenReturnNotFoundException() {
        when(repository.getReferenceById(any(UUID.class))).thenThrow(new ResourceNotFoundException(ID));
        when(repository.save(any())).thenReturn(barbearia);

        try {
            Barbearia response = service.updateResource(ID, barbeariaDTO);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O recurso com o ID: %s não foi encontrado", ID.toString()), e.getMessage());
        }
    }

    private void startBarbearia() {
        barbearia = new Barbearia(ID
                ,NOME
                ,CNPJ
                ,List.of());
        barbeariaDTO = new BarbeariaDTO(ID
                ,NOME
                ,CNPJ
                ,List.of());
        optional = Optional.of(new Barbearia(ID
                ,NOME
                ,CNPJ
                ,List.of()));
    }
}