package com.deizon.system_barbershop.domain.dtos;

import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class HorarioDTO {

    @EqualsAndHashCode.Include
    private UUID id;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    private Instant horarioInicial;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    private Instant horarioFinal;
    private Reserva reserva;

}
