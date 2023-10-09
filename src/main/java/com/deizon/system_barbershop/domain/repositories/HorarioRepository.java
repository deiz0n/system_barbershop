package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HorarioRepository extends JpaRepository<Horario, UUID> {
}
