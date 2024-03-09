package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    //ArrayList<Cliente> findAll();
    Optional<Cliente> findFirstByTelefone(String telefone);
    Optional<Cliente> findFirstByEmail(String email);
    //ArrayList<String> getDataByReservas(UUID id);

}
