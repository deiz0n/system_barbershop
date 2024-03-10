package com.deizon.system_barbershop.domain.dtos;

import com.deizon.system_barbershop.domain.enums.StatusReserva;
import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.models.Horario;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {

    @EqualsAndHashCode.Include
    private UUID id;
    private Cliente cliente;
    private Horario horario;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StatusReserva status;

}
