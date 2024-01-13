package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.HorarioDTO;
import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.models.Horario;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.deizon.system_barbershop.domain.repositories.HorarioRepository;
import com.deizon.system_barbershop.domain.repositories.ReservaRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.HorarioDTOMapper;
import com.deizon.system_barbershop.domain.services.exceptions.ArgumentNotValidException;
import com.deizon.system_barbershop.domain.services.exceptions.DataIntegrityException;
import com.deizon.system_barbershop.domain.services.exceptions.ExistingFieldException;
import com.deizon.system_barbershop.domain.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class HorarioServiceTest {

    public static final UUID ID = UUID.randomUUID();
    public static final Instant HORARIO_INICIAL = Instant.now();
    public static final Instant HORARIO_FINAL = HORARIO_INICIAL.plusSeconds(1260L);
    public static final Barbearia BARBEARIA = new Barbearia(UUID.randomUUID(), "Barbearia", "91259852000153", List.of());
    public static final int INDEX = 0;

    @InjectMocks
    private HorarioService service;
    @Mock
    private HorarioRepository repository;
    @Mock
    private HorarioDTOMapper mapper;
    @Mock
    private ReservaRepository reservaRepository;

    private Horario horario;
    private HorarioDTO horarioDTO;
    private Optional<Horario> optional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startHorario();
    }

    @Test
    void whenFindAllThenReturnListOfHorarioDTO() {
        when(repository.findAll()).thenReturn(List.of(horario));
        when(mapper.apply(any())).thenReturn(horarioDTO);

        List<HorarioDTO> response = service.findAll();

        assertNotNull(response);

        assertEquals(ArrayList.class, response.getClass());
        assertEquals(HorarioDTO.class, response.get(INDEX).getClass());
        assertEquals(1, response.size());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(HORARIO_INICIAL, response.get(INDEX).getHorarioInicial());
        assertEquals(HORARIO_FINAL, response.get(INDEX).getHorarioFinal());
        assertEquals(BARBEARIA, response.get(INDEX).getBarbearia());
    }

    @Test
    void whenFindByIDThenReturnHorarioDTO() {
        when(repository.findById(any(UUID.class))).thenReturn(optional);
        when(mapper.apply(horario)).thenReturn(horarioDTO);

        HorarioDTO response = service.findByID(ID);

        assertNotNull(response);

        assertEquals(HorarioDTO.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(HORARIO_INICIAL, response.getHorarioInicial());
        assertEquals(HORARIO_FINAL, response.getHorarioFinal());
        assertEquals(BARBEARIA, response.getBarbearia());
    }

    @Test
    void whenFindByIDThenReturnResourceNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        try {
            HorarioDTO response = service.findByID(ID);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O recurso com o ID: %s não foi encontrado", ID.toString()), e.getMessage());
        }
    }

    @Test
    void whenAddResourceThenReturnHorario() {
        when(repository.save(any())).thenReturn(horario);
        when(reservaRepository.findFirstByHorario(any())).thenReturn(Optional.empty());

        Horario response = service.addResource(horarioDTO);

        assertNotNull(response);

        assertEquals(Horario.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(HORARIO_INICIAL, response.getHorarioInicial());
        assertEquals(HORARIO_FINAL, response.getHorarioFinal());
        assertEquals(BARBEARIA, response.getBarbearia());
    }

    @Test
    void whenAddResourceThenReturnExistingHorarioException() {
        when(repository.findByBarbearia(any(Barbearia.class))).thenReturn(optional);
        when(repository.save(any())).thenReturn(horario);

        try {
            optional.get().setId(UUID.randomUUID());
            Horario response = service.addResource(horarioDTO);
        } catch (Exception e) {
            assertEquals(ExistingFieldException.class, e.getClass());
            assertEquals("Horário já cadastrado", e.getMessage());
        }
    }

    @Test
    void whenAddResourceThenReturnHorarioInvalidException() {
        when(repository.getReferenceById(any(UUID.class))).thenReturn(horario);
        when(repository.save(any())).thenReturn(horario);

        try {
            horarioDTO.setHorarioInicial(HORARIO_FINAL);
            horarioDTO.setHorarioFinal(HORARIO_INICIAL);
            Horario response = service.addResource(horarioDTO);
        } catch (Exception e) {
            assertEquals(ArgumentNotValidException.class, e.getClass());
            assertEquals("O horário inicial não pode ser posteior ao horário final.", e.getMessage());
        }

    }

    @Test
    void whenAddResourceThenReturnHorarioShortException() {
        when(repository.getReferenceById(any(UUID.class))).thenReturn(horario);
        when(repository.save(any())).thenReturn(horario);

        try {
            horarioDTO.setHorarioInicial(Instant.now().plusSeconds(200L));
            Horario response = service.addResource(horarioDTO);
        } catch (Exception e) {
            assertEquals(ArgumentNotValidException.class, e.getClass());
            assertEquals("Intervalo de tempo muito curto. Tente novamente", e.getMessage());
        }
    }

    @Test
    void whenRemResourceThenReturnVoid() {
        doNothing().when(repository).deleteById(any(UUID.class));
        when(repository.findById(any(UUID.class))).thenReturn(optional);

        service.remResource(ID);

        verify(repository, times(1)).deleteById(ID);
    }

    @Test
    void whenRemResourceThenReturnResourceNotFoundException() {
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
        when(repository.findById(any(UUID.class))).thenReturn(optional);
        when(reservaRepository.findFirstByHorario(any(Horario.class))).thenReturn(Optional.of(new Reserva()));

        try {
            service.remResource(ID);
        } catch (Exception e) {
            assertEquals(DataIntegrityException.class, e.getClass());
            assertEquals("O horário não pode ser excluído pois está vinculado a uma reserva.", e.getMessage());
        }
    }

    @Test
    void whenUpdateResourceThenReturnHorario() {
        when(repository.getReferenceById(any(UUID.class))).thenReturn(horario);
        when(repository.save(any())).thenReturn(horario);

        Horario response = service.updateResource(ID, horarioDTO);

        assertNotNull(response);

        assertEquals(Horario.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(HORARIO_INICIAL, response.getHorarioInicial());
        assertEquals(HORARIO_FINAL, response.getHorarioFinal());
        assertEquals(BARBEARIA, response.getBarbearia());
    }

    @Test
    void whenUpdateResourceThenReturnResourceNotFoundException() {
        when(repository.getReferenceById(any(UUID.class))).thenThrow(new ResourceNotFoundException(ID));
        when(repository.save(any())).thenReturn(horario);

        try {
            Horario response = service.updateResource(ID, horarioDTO);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O recurso com o ID: %s não foi encontrado", ID.toString()), e.getMessage());
        }
    }

    @Test
    void whenUpdateResourceThenReturnExistingHorarioException() {
        when(repository.findByBarbearia(any(Barbearia.class))).thenReturn(optional);
        when(repository.getReferenceById(any(UUID.class))).thenReturn(horario);
        when(repository.save(any())).thenReturn(horario);

        try {
            optional.get().setId(UUID.randomUUID());
            Horario response = service.updateResource(ID, horarioDTO);
        } catch (Exception e) {
            assertEquals(ExistingFieldException.class, e.getClass());
            assertEquals("Horário já cadastrado", e.getMessage());
        }
    }

    @Test
    void whenUpdateResourceThenReturnArgumenteNotValidException() {
        when(repository.findByBarbearia(any(Barbearia.class))).thenReturn(optional);
        when(repository.getReferenceById(any(UUID.class))).thenReturn(horario);
        when(repository.save(any())).thenReturn(horario);


        try {
            horarioDTO.setHorarioInicial(HORARIO_FINAL);
            horarioDTO.setHorarioFinal(HORARIO_INICIAL);
            Horario response = service.updateResource(ID, horarioDTO);
        } catch (Exception e) {
            assertEquals(ArgumentNotValidException.class, e.getClass());
            assertEquals("O horário inicial não pode ser posteior ao horário final.", e.getMessage());
        }
    }

    @Test
    void whenUpdateResourceThenReturnHorarioShort() {
        when(repository.findByBarbearia(any(Barbearia.class))).thenReturn(optional);
        when(repository.getReferenceById(any(UUID.class))).thenReturn(horario);
        when(repository.save(any())).thenReturn(horario);

        try {
            horarioDTO.setHorarioInicial(HORARIO_INICIAL);
            horarioDTO.setHorarioFinal(HORARIO_INICIAL);
            Horario response = service.updateResource(ID, horarioDTO);
        } catch (Exception e) {
            assertEquals(ArgumentNotValidException.class, e.getClass());
            assertEquals("Intervalo de tempo muito curto. Tente novamente", e.getMessage());
        }
    }

    private void startHorario() {
        horario = new Horario(ID
                ,HORARIO_INICIAL
                ,HORARIO_FINAL
                ,BARBEARIA
                ,new Reserva());
        horarioDTO = new HorarioDTO(ID
                ,HORARIO_INICIAL
                ,HORARIO_FINAL
                ,BARBEARIA);
        optional = Optional.of(new Horario(ID
                ,HORARIO_INICIAL
                ,HORARIO_FINAL
                ,BARBEARIA
                ,new Reserva()));
    }
}