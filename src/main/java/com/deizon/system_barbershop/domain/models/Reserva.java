package com.deizon.system_barbershop.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne
    private Cliente cliente;

    @OneToOne
    private Horario horario;
}
