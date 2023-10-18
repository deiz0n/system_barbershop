package com.deizon.system_barbershop.domain.models;

import jakarta.persistence.*;
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
@Entity(name = "tb_barbearia")
public class Barbearia {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String nome;
    private String cnpj;

    @OneToMany(mappedBy = "barbearia")
    private List<Horario> horarios;

}
