package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BarbeariaRepository extends JpaRepository<Barbearia, UUID> {
}
