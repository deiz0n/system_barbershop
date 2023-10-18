package com.deizon.system_barbershop.domain.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
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

}
