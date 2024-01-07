package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.models.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BarbeariaRepository extends JpaRepository<Barbearia, UUID> {

    Optional<Barbearia> findFirstByNome(String nome);

    Optional<Barbearia> findFirstByCnpj(String cnpj);

    String getDataByReserva(UUID id);

}
