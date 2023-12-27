package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BarbeariaRepository extends JpaRepository<Barbearia, UUID> {

    Optional<Barbearia> getBarbeariaByNome(String nome);

    Optional<Barbearia> getBarbeariaByCnpj(String cnpj);

    String getDataByReserva(UUID id);
}
