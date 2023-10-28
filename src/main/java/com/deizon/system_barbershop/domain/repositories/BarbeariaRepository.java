package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BarbeariaRepository extends JpaRepository<Barbearia, UUID> {

    @Query("FROM tb_barbearia r left join fetch r.horarios")
    List<Barbearia> findAll();
}
