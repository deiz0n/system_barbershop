package com.deizon.system_barbershop.domain.services.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(ExistingFieldException.class)
    public ResponseEntity<?> existingField(ExistingFieldException exception, HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> argumentInvalid(MethodArgumentNotValidException exception, HttpServletRequest request) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(exception.getFieldError().getField().toUpperCase());
        stringBuilder.append(" inválido.");
        var error = new Error(
                Instant.now(),
                HttpStatus.BAD_REQUEST,
                stringBuilder.toString(),
                request.getRequestURI()
        );
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> dateInvalid(HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                HttpStatus.BAD_REQUEST,
                "O horário informado possui formato inválido.",
                request.getRequestURI()
        );
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(ArgumentNotValidException.class)
    public ResponseEntity<?> dateShort(ArgumentNotValidException exception, HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> contraintVioletion(SQLIntegrityConstraintViolationException exception, HttpServletRequest request) {
        var msg = new String();
        if (exception.getMessage().contains("FK40agr0nhu8t21hlb0s4bifbsp")) {
            msg = "O horário informado não foi encontrado.";
        }
        if(exception.getMessage().contains("FK14ooegqc541axpd59bmvmbw1p")) {
            msg = "O cliente informado não foi encontrado.";
        }
        var error = new Error(
                Instant.now(),
                HttpStatus.BAD_REQUEST,
                msg,
                request.getRequestURI()
        );
    return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<?> dataIntegrity(DataIntegrityException exception, HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(409).body(error);
    }

}
