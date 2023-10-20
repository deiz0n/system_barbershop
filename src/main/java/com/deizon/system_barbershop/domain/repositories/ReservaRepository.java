package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {
}
