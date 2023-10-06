package com.deizon.system_barbershop.domain.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record HorarioDTO(
        @NotBlank(message = "Esse campo não pode ficar em branco")
        LocalDateTime horario_inicial,
        @NotBlank(message = "Esse campo não pode ficar em branco")
        LocalDateTime horario_final
) {
}
