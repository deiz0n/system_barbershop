package com.deizon.system_barbershop.domain.dtos;

import com.deizon.system_barbershop.domain.models.Reserva;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    @EqualsAndHashCode.Include
    private UUID id;
    @NotBlank(message = "Esse campo não pode ficar em branco")
    private String nome;
    @CPF(message = "CPF inválido. Tente novamente!")
    @NotBlank(message = "Esse campo não pode ficar em branco")
    private String cpf;
    @NotBlank(message = "Esse campo não pode ficar em branco")
    @Size(min = 11, message = "Número de telefone inválido, o número precisa ter ao menos 11 digitos")
    private String telefone;
    @Email(message = "E-mail inválido. Tente novamente!")
    @NotBlank(message = "Esse campo não pode ficar em branco")
    private String email;
    private List<Reserva> reservas;

}
