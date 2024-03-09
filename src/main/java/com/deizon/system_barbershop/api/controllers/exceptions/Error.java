package com.deizon.system_barbershop.api.controllers.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class Error {

    private LocalDateTime timestamp;
    private HttpStatus httpStatus;
    private String error;
    private String path;

    @JsonIgnore
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm:ss");

    public String getTimestamp() {
        return timestamp.format(formatter);
    }
}
