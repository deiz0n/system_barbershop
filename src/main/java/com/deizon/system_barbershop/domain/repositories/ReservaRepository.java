package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {

    @Query("FROM tb_reserva r JOIN FETCH r.horario JOIN FETCH r.cliente")
    ArrayList<Reserva> findAll();
}
