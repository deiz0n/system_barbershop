package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.BarbeariaDTO;
import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.repositories.BarbeariaRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.BarbeariaDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BarbeariaServiceTest {

    public static final UUID ID = UUID.randomUUID();
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startBarbearia();
    }

    @Test
    void whenFindAllThenReturnListOfUsers() {
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
    void findByID() {
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
    }
}