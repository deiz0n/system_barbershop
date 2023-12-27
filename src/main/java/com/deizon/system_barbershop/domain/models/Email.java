package com.deizon.system_barbershop.domain.models;

import jakarta.persistence.*;
import lombok.*;

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
