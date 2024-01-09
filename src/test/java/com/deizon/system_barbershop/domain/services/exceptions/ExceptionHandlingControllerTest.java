package com.deizon.system_barbershop.domain.services.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
class ExceptionHandlingControllerTest<T> {

    public static final UUID ID = UUID.fromString("4329cedf-4521-467a-a0ad-e23fad5f638d");

    @InjectMocks
    private ExceptionHandlerController handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenResourceNotFoundThenReturnNotFound() {
        ResponseEntity<Error> response = handler.resourceNotFound(
                new ResourceNotFoundException(ID), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Error.class, response.getBody().getClass());
        assertEquals(String.format("O recurso com o ID: %s não foi encontrado", ID.toString()), response.getBody().getError());
    }

    @Test
    void whenExistingFieldThenReturnConflict() {
        ResponseEntity<Error> response = handler.existingField(
                new ExistingFieldException("Campo já cadastrado"), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Error.class, response.getBody().getClass());
        assertEquals("Campo já cadastrado", response.getBody().getError());
    }

//    @Test
//    void whenArgumentInvalidThenReturnBadRequest() {
//        ResponseEntity<?> response = handler.argumentInvalid(
//                new MethodArgumentNotValidException(ID), new MockHttpServletRequest());
//    }

    @Test
    void whenDateInvalidThenReturnBadRequest() {
        ResponseEntity<Error> response = handler.dataInvalid(new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Error.class, response.getBody().getClass());
        assertEquals("O horário informado possui formato inválido.", response.getBody().getError());
    }

    @Test
    void whenDataShortThenReturnBadRequest() {
        ResponseEntity<Error> response = handler.dataShort(
                new ArgumentNotValidException("Intervalo de tempo muito curto. Tente novamente"), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Error.class, response.getBody().getClass());
        assertEquals("Intervalo de tempo muito curto. Tente novamente", response.getBody().getError());
    }

    @Test
    void whenContraintVioletionThenReturnBadRequest() {
        ResponseEntity<Error> response = handler.contraintVioletion(
                new SQLIntegrityConstraintViolationException("FK40agr0nhu8t21hlb0s4bifbsp"), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Error.class, response.getBody().getClass());
        assertEquals("O horário informado não foi encontrado.", response.getBody().getError());
    }

    @Test
    void whenDataIntegrityThenReturnConflict() {
        ResponseEntity<Error> response = handler.dataIntegrity(
                new DataIntegrityException(""), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.CONFLICT, response.getBody().getHttpStatus());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Error.class, response.getBody().getClass());
        assertEquals("", response.getBody().getError());
    }
}