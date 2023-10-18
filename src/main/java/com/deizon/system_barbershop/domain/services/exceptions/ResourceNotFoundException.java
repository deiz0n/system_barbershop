package com.deizon.system_barbershop.domain.services.exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(UUID id) {
        super(String.format("O recurso com o ID: %s não foi encontrado", id.toString()));
    }

}
