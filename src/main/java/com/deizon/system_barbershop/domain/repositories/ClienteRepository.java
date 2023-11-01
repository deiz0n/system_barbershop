package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    @Query("FROM tb_cliente c LEFT JOIN FETCH c.reservas")
    List<Cliente> findAll();

    @Query("SELECT c.cpf FROM tb_cliente c")
    List<String> existsCPF();

    @Query("SELECT c.telefone FROM tb_cliente c")
    List<String> existsTelefone();

    @Query("SELECT c.email FROM tb_cliente c")
    List<String> existsEmail();

}
