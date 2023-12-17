package com.deizon.system_barbershop.domain.services.exceptions;

public class SendEmailException extends RuntimeException{

    public SendEmailException(String msg) {
        super(msg);
    }

}
