package com.deizon.system_barbershop.domain.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Barbearia {

    @EqualsAndHashCode.Include
    private UUID id;
    private String nome;
    private String cnpj;

}
