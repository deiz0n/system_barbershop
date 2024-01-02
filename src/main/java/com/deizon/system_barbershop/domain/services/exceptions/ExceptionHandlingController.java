package com.deizon.system_barbershop.domain.services.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFound(ResourceNotFoundException exception, WebRequest request) {
        var error = new Error(
                Instant.now(),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(ExistingFieldException.class)
    public ResponseEntity<?> existingField(ExistingFieldException exception, WebRequest request) {
        var error = new Error(
                Instant.now(),
                HttpStatus.CONFLICT,
                exception.getMessage().toUpperCase(),
                request.getDescription(false)
        );
        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> argumentInvalid(MethodArgumentNotValidException exception, WebRequest request) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(exception.getFieldError().getField().toUpperCase());
        stringBuilder.append(" INVÁLIDO");
        var error = new Error(
                Instant.now(),
                HttpStatus.BAD_REQUEST,
                stringBuilder.toString(),
                request.getDescription(false)
        );
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> dateInvalid(WebRequest request) {
        var error = new Error(
                Instant.now(),
                HttpStatus.BAD_REQUEST,
                "O HORÁRIO INFORMADO POSSUI FORMATO INVÁLIDO",
                request.getDescription(false)
        );
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(ArgumentNotValidException.class)
    public ResponseEntity<?> dateShort(ArgumentNotValidException exception, WebRequest request) {
        var error = new Error(
                Instant.now(),
                HttpStatus.BAD_REQUEST,
                exception.getMessage().toUpperCase(),
                request.getDescription(false)
        );
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> contraintVioletion(SQLIntegrityConstraintViolationException exception, WebRequest request) {
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
                request.getDescription(false)
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
