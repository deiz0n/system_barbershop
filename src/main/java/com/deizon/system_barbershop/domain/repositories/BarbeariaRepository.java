package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface BarbeariaRepository extends JpaRepository<Barbearia, UUID> {

    @Query("SELECT b.nome FROM tb_barbearia b")
    ArrayList<String> existsNome();

    @Query("SELECT b.cnpj FROM tb_barbearia b")
    ArrayList<String> existsCNPJ();

    String getDataByReserva(UUID id);
}
