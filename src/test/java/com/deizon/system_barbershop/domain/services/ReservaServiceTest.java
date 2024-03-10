package com.deizon.system_barbershop.domain.services;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class ReservaServiceTest {

    /*
    public static final Integer INDEX = 0;
    public static final UUID ID = UUID.randomUUID();
    public static final Cliente CLIENTE = new Cliente(ID, "Eduardo", "94027732073", "1140028922", "teste@gmail.com", List.of());
    public static final Horario HORARIO = new Horario(ID, Instant.now(), Instant.now().plusSeconds(2160L), new Barbearia(), new Reserva());

    @InjectMocks
    private ReservaService service;
    @Mock
    private ReservaRepository repository;
    @Mock
    private ReservaDTOMapper mapper;

    private Reserva reserva;
    private ReservaDTO reservaDTO;
    private Optional<Reserva> optional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startReserva();
    }

    @Test
    void whenFindAllThenReturnListOfReservaDTO() {
        when(repository.findAll()).thenReturn(List.of(reserva));
        when(mapper.apply(reserva)).thenReturn(reservaDTO);

        List<ReservaDTO> response = service.findAll();

        assertNotNull(response);

        assertEquals(1, response.size());
        assertEquals(ArrayList.class, response.getClass());
        assertEquals(ReservaDTO.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(CLIENTE, response.get(INDEX).getCliente());
        assertEquals(HORARIO, response.get(INDEX).getHorario());
    }

    @Test
    void  whenFindByIDThenReturnReservaDTO() {
        when(repository.findById(any(UUID.class))).thenReturn(optional);
        when(mapper.apply(reserva)).thenReturn(reservaDTO);

        ReservaDTO response = service.findByID(ID);

        assertNotNull(response);

        assertEquals(ReservaDTO.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(CLIENTE, response.getCliente());
        assertEquals(HORARIO, response.getHorario());
    }

    @Test
    void whenFindByIDThenReturnResourceNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        try {
            ReservaDTO response = service.findByID(ID);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O recurso com o ID: %s não foi encontrado", ID.toString()), e.getMessage());
        }

    }

    @Test
    void whenAddResourceThenReturnReserva() {
        when(repository.findFirstByHorario(any(Horario.class))).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(reserva);

        Reserva response = service.addResource(reservaDTO);

        assertNotNull(response);

        assertEquals(Reserva.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(CLIENTE, response.getCliente());
        assertEquals(HORARIO, response.getHorario());
    }

    @Test
    void whenAddResourceThenReturnDataIntegrityException() {
        when(repository.findFirstByHorario(any(Horario.class))).thenReturn(optional);
        when(repository.save(any())).thenReturn(reserva);

        try {
            Reserva response = service.addResource(reservaDTO);
        } catch (Exception e) {
            assertEquals(DataIntegrityException.class, e.getClass());
            assertEquals("O horário já está vinculado a uma reserva", e.getMessage());
        }
    }

    @Test
    void whenRemResourceThenReturnVoid() {
        when(repository.findById(any(UUID.class))).thenReturn(optional);

        doNothing().when(repository).deleteById(any(UUID.class));

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
    void whenUpdateResourceThenReturnReserva() {
        when(repository.getReferenceById(any(UUID.class))).thenReturn(reserva);
        when(repository.save(any())).thenReturn(reserva);
        when(repository.findFirstByHorario(any(Horario.class))).thenReturn(Optional.empty());

        Reserva response = service.updateResource(ID, reservaDTO);

        assertNotNull(response);

        assertEquals(Reserva.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(CLIENTE, response.getCliente());
        assertEquals(HORARIO, response.getHorario());
    }

    @Test
    void whenUpdateResourceThenReturnDataIntegrityException() {
        when(repository.getReferenceById(any(UUID.class))).thenReturn(reserva);
        when(repository.save(any())).thenReturn(reserva);
        when(repository.findFirstByHorario(any(Horario.class))).thenReturn(optional);

        try {
            Reserva response = service.updateResource(ID, reservaDTO);
        } catch (Exception e) {
            assertEquals(DataIntegrityException.class, e.getClass());
            assertEquals("O horário já está vinculado a uma reserva", e.getMessage());
        }

    }

    @Test
    void whenUpdateResourceThenReturnResourceNotFoundException() {
        when(repository.getReferenceById(any(UUID.class))).thenThrow(new ResourceNotFoundException(ID));
        when(repository.save(any())).thenReturn(reserva);
        when(repository.findFirstByHorario(any(Horario.class))).thenReturn(Optional.empty());

        try {
            Reserva response = service.updateResource(ID, reservaDTO);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O recurso com o ID: %s não foi encontrado", ID.toString()), e.getMessage());
        }

    }

    private void startReserva() {
        reserva = new Reserva(ID, CLIENTE, HORARIO);
        reservaDTO = new ReservaDTO(ID, CLIENTE, HORARIO);
        optional = Optional.of(new Reserva(ID, CLIENTE, HORARIO));
    }

     */
}