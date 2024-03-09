package com.deizon.system_barbershop.api.controllers.exceptions;

import com.deizon.system_barbershop.domain.services.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handleResourceNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        var error = new Error(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(ExistingFieldException.class)
    public ResponseEntity<Error> handleExistingField(ExistingFieldException exception, HttpServletRequest request) {
        var error = new Error(
                LocalDateTime.now(),
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleFieldInvalid(MethodArgumentNotValidException exception, HttpServletRequest request) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(exception.getFieldError().getField().toUpperCase());
        stringBuilder.append(" inválido.");
        var error = new Error(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                stringBuilder.toString(),
                request.getRequestURI()
        );
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleHTTPMessageNotReadable(HttpServletRequest request) {
        var error = new Error(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "O horário informado possui formato inválido.",
                request.getRequestURI()
        );
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(ArgumentNotValidException.class)
    public ResponseEntity<Error> handleArgumentNotValid(ArgumentNotValidException exception, HttpServletRequest request) {
            Error error = new Error(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST,
                    exception.getMessage(),
                    request.getRequestURI()
        );
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Error> handleSQLIntegrityConstraintViolation(SQLIntegrityConstraintViolationException exception, HttpServletRequest request) {
        var msg = new String();
        if (exception.getMessage().contains("FK40agr0nhu8t21hlb0s4bifbsp")) {
            msg = "O horário informado não foi encontrado.";
        }
        if(exception.getMessage().contains("FK14ooegqc541axpd59bmvmbw1p")) {
            msg = "O cliente informado não foi encontrado.";
        }
        var error = new Error(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                msg,
                request.getRequestURI()
        );
    return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<Error> handleDataIntegrity(DataIntegrityException exception, HttpServletRequest request) {
        var error = new Error(
                LocalDateTime.now(),
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(SendEmailException.class)
    public ResponseEntity<Error> handleSendEmail(SendEmailException exception, HttpServletRequest request) {
        var error = new Error(
                LocalDateTime.now()
                ,HttpStatus.INTERNAL_SERVER_ERROR
                ,exception.getMessage()
                ,request.getRequestURI()
        );
        return ResponseEntity.status(500).body(error);
    }
}
