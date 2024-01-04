package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.BarbeariaDTO;
import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.repositories.BarbeariaRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.BarbeariaDTOMapper;
import com.deizon.system_barbershop.domain.services.exceptions.ResourceNotFoundException;
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
import static org.mockito.Mockito.when;

@SpringBootTest
class BarbeariaServiceTest {

    public static final UUID ID = UUID.fromString("261254e5-6683-447c-9c2a-43e153339773");
    public static final String NOME = "barbearia 1";
    public static final String CNPJ = "84623997000126";
    public static final Integer INDEX = 1;

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

        List<BarbeariaDTO> response = service.findAll();
        response.add(barbeariaDTO);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(BarbeariaDTO.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NOME, response.get(INDEX).getNome());
        assertEquals(CNPJ, response.get(INDEX).getCnpj());
    }

    @Test
    void whenFindByIDThenReturnResourceNotFoundException() {
        when(repository.findById(any(UUID.class))).thenThrow(new ResourceNotFoundException(ID));

        try {
            var response = service.findByID(ID);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O recurso com o ID: %s não foi encontrado", ID.toString()), e.getMessage());
        }
    }

    @Test
    void addResource() {
    }

    @Test
    void remResource() {
    }

    @Test
    void updateDataResource() {
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