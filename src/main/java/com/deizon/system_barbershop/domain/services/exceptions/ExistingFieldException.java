package com.deizon.system_barbershop.domain.services.exceptions;

public class ExistingFieldException extends RuntimeException{

    public ExistingFieldException(String msg) {
        super(msg);
    }

}
