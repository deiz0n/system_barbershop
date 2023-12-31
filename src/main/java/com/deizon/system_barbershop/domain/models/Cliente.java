package com.deizon.system_barbershop.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_cliente")
public class Cliente {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;


    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "cliente")
    private List<Reserva> reservas;

}
