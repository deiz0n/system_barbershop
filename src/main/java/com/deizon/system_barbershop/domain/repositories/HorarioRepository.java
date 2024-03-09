package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.models.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface HorarioRepository extends JpaRepository<Horario, UUID> {

    //Optional<Horario> findByBarbearia(Barbearia barbearia);

}
