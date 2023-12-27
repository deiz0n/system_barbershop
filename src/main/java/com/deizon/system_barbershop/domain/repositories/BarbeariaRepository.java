package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface BarbeariaRepository extends JpaRepository<Barbearia, UUID> {

    @Query("FROM tb_barbearia r LEFT JOIN FETCH r.horarios")
    ArrayList<Barbearia> findAll();

    @Query("SELECT b.nome FROM tb_barbearia b")
    ArrayList<String> existsNome();

    @Query("SELECT b.cnpj FROM tb_barbearia b")
    ArrayList<String> existsCNPJ();

    @Query("SELECT b.nome FROM tb_barbearia b INNER JOIN tb_horario h ON b.id = h.barbearia.id INNER JOIN tb_reserva r ON r.horario.id = h.id")
    String getDataByReserva(UUID id);
}
