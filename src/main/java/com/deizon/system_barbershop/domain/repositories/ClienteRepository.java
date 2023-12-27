package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    @Query("FROM tb_cliente c LEFT JOIN FETCH c.reservas")
    ArrayList<Cliente> findAll();

    @Query("SELECT c.cpf FROM tb_cliente c")
    ArrayList<String> existsCPF();

    @Query("SELECT c.telefone FROM tb_cliente c")
    ArrayList<String> existsTelefone();

    @Query("SELECT c.email FROM tb_cliente c")
    ArrayList<String> existsEmail();

    @Query("SELECT c.nome FROM tb_cliente c INNER JOIN tb_reserva r ON c.id = r.cliente.id WHERE r.id = :id UNION ALL SELECT c.email FROM tb_cliente c INNER JOIN tb_reserva r ON c.id = r.cliente.id WHERE r.id = :id")
    ArrayList<String> getDataByReservas(UUID id);

}
