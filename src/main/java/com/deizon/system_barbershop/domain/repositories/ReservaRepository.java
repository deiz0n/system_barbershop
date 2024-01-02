package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.models.Horario;
import com.deizon.system_barbershop.domain.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {

    Optional<Reserva> findByCliente(Cliente cliente);
    Optional<Reserva> findByHorario(Horario horario);

}
