package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface HorarioRepository extends JpaRepository<Horario, UUID> {

    ArrayList<Instant> existsHorario();

    ArrayList<Instant> getDataByReservas(UUID id);

}
