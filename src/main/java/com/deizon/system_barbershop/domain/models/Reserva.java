package com.deizon.system_barbershop.domain.models;

import com.deizon.system_barbershop.domain.enums.StatusReserva;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "tb_reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;
    private StatusReserva status;

    @JsonIgnore
    @ManyToOne
    private Cliente cliente;

    @OneToOne
    private Horario horario;
}
