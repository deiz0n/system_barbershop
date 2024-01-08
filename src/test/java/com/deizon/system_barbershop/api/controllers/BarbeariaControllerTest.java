package com.deizon.system_barbershop.api.controllers;

import com.deizon.system_barbershop.domain.dtos.BarbeariaDTO;
import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.services.BarbeariaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BarbeariaControllerTest {

    public static final UUID ID = UUID.randomUUID();
    public static final String NOME = "Dudu Cortas";
    public static final String CNPJ = "96852528000168";
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
        assertEquals(CNPJ,response.getBody().get(INDEX).getCnpj());
    }

    @Test
    void getBarbearia() {
    }

    @Test
    void createBarbearia() {
    }

    @Test
    void deleteBarbearia() {
    }

    @Test
    void updateBarbearia() {
    }

    private void startBarbearia() {
        barbearia = new Barbearia(ID
                ,NOME
                ,CNPJ
                , List.of());
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