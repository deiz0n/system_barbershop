package com.deizon.system_barbershop.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    private UUID id;
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String text;
    private Instant sendDateEmail;

}
