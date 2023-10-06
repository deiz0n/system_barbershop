package com.deizon.system_barbershop.domain.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteDTO(
        @NotBlank(message = "Esse campo não pode ficar em branco")
        String nome,
        @CPF(message = "CPF inválido. Tente novamente!") @NotBlank(message = "Esse campo não pode ficar em branco")
        String cpf,
        @NotBlank(message = "Esse campo não pode ficar em branco") @Size(min = 11, message = "Número de telefone incálido, o número precisa ter ao menos 11 digitos")
        String telefone,
        @Email(message = "E-mail inválido. Tente novamente!") @NotBlank(message = "Esse campo não pode ficar em branco")
        String email
) {
}
