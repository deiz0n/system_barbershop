package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BarbeariaRepository extends JpaRepository<Barbearia, UUID> {

    Optional<Barbearia> findFirstByNome(String nome);
    Optional<Barbearia> findFirstByEmail(String nome);
    //String getDataByReserva(UUID id);
    //Optional<String> getDataByReserva(UUID id);

}
