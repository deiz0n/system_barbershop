package com.deizon.system_barbershop.domain.services.exceptions;

public class ArgumentNotValidException extends RuntimeException{

    public ArgumentNotValidException(String msg) {
        super(msg);
    }

}
