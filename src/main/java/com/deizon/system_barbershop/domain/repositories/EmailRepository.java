package com.deizon.system_barbershop.domain.repositories;

import com.deizon.system_barbershop.domain.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<Email, UUID> {
}
