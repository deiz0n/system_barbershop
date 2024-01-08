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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

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
        ResponseEntity<?> response = handler.resourceNotFound(
                new ResourceNotFoundException(ID), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Error.class, response.getBody().getClass());
    }

    @Test
    void whenExistingFieldThenReturnConflict() {
        ResponseEntity<?> response = handler.existingField(
                new ExistingFieldException("Campo já cadastrado"), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Error.class, response.getBody().getClass());
    }

//    @Test
//    void whenArgumentInvalidThenReturnBadRequest() {
//        ResponseEntity<?> response = handler.argumentInvalid(
//                new MethodArgumentNotValidException(ID), new MockHttpServletRequest());
//    }

    @Test
    void whenDateInvalidThenReturnBadRequest() {
        ResponseEntity<?> response = handler.dateInvalid(new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Error.class, response.getBody().getClass());
    }

    @Test
    void dateShort() {
    }

    @Test
    void contraintVioletion() {
    }

    @Test
    void dataIntegrity() {
    }
}