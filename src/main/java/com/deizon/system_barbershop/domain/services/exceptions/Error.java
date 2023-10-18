package com.deizon.system_barbershop.domain.services.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    private Instant timestamp;
    private HttpStatus httpStatus;
    private String error;
    private String path;

}
