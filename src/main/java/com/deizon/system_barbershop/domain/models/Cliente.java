package com.deizon.system_barbershop.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {

    private UUID id;
    private String nome;
    private String cpf;
    private String telefone;
    private String emaiil;

}
