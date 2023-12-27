package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    ArrayList<Cliente> findAll();

    Optional<Cliente> getClienteByCpf(String cpf);

    Optional<Cliente> getClienteByTelefone(String telefone);

    Optional<Cliente> getClienteByEmail(String email);

    ArrayList<String> getDataByReservas(UUID id);

}
