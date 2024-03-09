package com.deizon.system_barbershop.api;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HorarioControllerTest {

    /*
   public static final String HORARIO_INICIAL = String.valueOf(Instant.now());
   public static final Instant HORARIO_FINAL = HORARIO_INICIAL.plusSeconds(1260L);
   public static final Barbearia BARBEARIA = new Barbearia(UUID.randomUUID(), "Barbearia", List.of());
   public static final int INDEX = 0;

   @InjectMocks
   private HorarioController controller;
   @Mock
   private HorarioService service;
   @Mock
   private HorarioDTOMapper mapper;

   private Horario horario;
   private HorarioDTO horarioDTO;
   private Optional<Horario> optional;
   @BeforeEach
   void setUp() {
       MockitoAnnotations.openMocks(this);
       startHorario();
   }



    public static final UUID ID = UUID.randomUUID();

    @Test
    void whenGetHorariosThenReturnOk() {
        when(service.findAll()).thenReturn(List.of(horarioDTO));

        ResponseEntity<List<HorarioDTO>> response = controller.getHorarios();

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HorarioDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(HORARIO_INICIAL, response.getBody().get(INDEX).getHorarioInicial());
        assertEquals(HORARIO_FINAL, response.getBody().get(INDEX).getHorarioFinal());
        assertEquals(BARBEARIA, response.getBody().get(INDEX).getBarbearia());
    }

    @Test
    void whenGetHorarioThenReturnOk() {
        when(service.findByID(any(UUID.class))).thenReturn(horarioDTO);

        ResponseEntity<HorarioDTO> response = controller.getHorario(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HorarioDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(HORARIO_INICIAL, response.getBody().getHorarioInicial());
        assertEquals(HORARIO_FINAL, response.getBody().getHorarioFinal());
        assertEquals(BARBEARIA, response.getBody().getBarbearia());
    }

    @Test
    void whenCreateHorarioThenReturnOk() {
        when(service.addResource(any())).thenReturn(horario);

        ResponseEntity<?> response = controller.createHorario(horarioDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Horario.class, response.getBody().getClass());
    }

    @Test
    void whenDeleteHorarioThenReturnNoContent() {
        doNothing().when(service).remResource(any(UUID.class));

        ResponseEntity<?> response = controller.deleteHorario(ID);

        assertNotNull(response);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());

        verify(service, times(1)).remResource(ID);
    }

    @Test
    void whenUpdateHorarioThenReturnOk() {
        when(service.updateResource(any(UUID.class), any())).thenReturn(horario);

        ResponseEntity<Horario> response = controller.updateHorario(ID, horarioDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Horario.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(HORARIO_INICIAL, response.getBody().getHorarioInicial());
        assertEquals(HORARIO_FINAL, response.getBody().getHorarioFinal());
        assertEquals(BARBEARIA, response.getBody().getBarbearia());
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

     */
}