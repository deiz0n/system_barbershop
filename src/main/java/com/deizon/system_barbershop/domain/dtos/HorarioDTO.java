package com.deizon.system_barbershop.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class HorarioDTO {

    @EqualsAndHashCode.Include
    private UUID id;
    private LocalDateTime horarioInicial;
    private LocalDateTime horarioFinal;

}
