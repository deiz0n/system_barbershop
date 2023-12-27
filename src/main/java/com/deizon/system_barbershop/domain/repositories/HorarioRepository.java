package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface HorarioRepository extends JpaRepository<Horario, UUID> {

    @Query("SELECT i.horarioInicial FROM tb_horario i UNION SELECT f.horarioFinal FROM tb_horario f")
    ArrayList<Instant> existsHorario();

    @Query("SELECT h.horarioInicial FROM tb_horario h INNER JOIN tb_reserva r ON h.id = r.horario.id WHERE r.id = :id")
    ArrayList<Instant> getDataByReservas(UUID id);

}
