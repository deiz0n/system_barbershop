package com.deizon.system_barbershop.domain.dtos;

import com.deizon.system_barbershop.domain.models.Barbearia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class HorarioDTO {

    @EqualsAndHashCode.Include
    private UUID id;
    private Instant horarioInicial;
    private Instant horarioFinal;
    private Barbearia barbearia;

}
