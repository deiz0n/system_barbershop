package com.deizon.system_barbershop.domain.dtos;

import com.deizon.system_barbershop.domain.models.Horario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class BarbeariaDTO {

    @EqualsAndHashCode.Include
    private UUID id;
    @NotBlank(message = "Esse campo não pode ficar em branco")
    private String nome;
    @JsonIgnore
    private List<Horario> horarios;
    private String email;
}
