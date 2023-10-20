package com.deizon.system_barbershop.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_horario")
public class Horario {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Instant horarioInicial;
    private Instant horarioFinal;

    @ManyToOne
    private Barbearia barbearia;

    @OneToOne
    private Reserva reserva;

}
