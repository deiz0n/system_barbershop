package com.deizon.system_barbershop.domain.services.exceptions;

import com.deizon.system_barbershop.domain.models.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

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
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> argumentInvalid(MethodArgumentNotValidException exception, WebRequest request) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(exception.getFieldError().getField().toUpperCase().toUpperCase());
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
    public ResponseEntity<?> dataInvalid(HttpMessageConversionException exception, WebRequest request) {
        var error = new Error(
                Instant.now(),
                HttpStatus.BAD_REQUEST,
                "O HORÁRIO INFORMADO POSSUI FORMATO INVÁLIDOI",
                request.getDescription(false)
        );
        return ResponseEntity.status(400).body(error);
    }

}
