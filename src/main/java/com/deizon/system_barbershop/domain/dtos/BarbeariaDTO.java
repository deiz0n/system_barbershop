package com.deizon.system_barbershop.domain.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record BarbeariaDTO(
        @NotBlank(message = "Esse campo não pode ficar em branco")
        String nome,
        @CNPJ(message = "CNPJ inválido. Tente novamente!") @NotBlank(message = "Esse campo não pode ficar em branco")
        String cnpj
) {
}
