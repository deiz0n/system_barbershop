package com.deizon.system_barbershop.domain.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Horario {

    @EqualsAndHashCode.Include
    private UUID id;
    private LocalDateTime horarioInicial;
    private LocalDateTime horarioFinal;

}
