package com.deizon.system_barbershop.domain.services.DTOMapper;

import com.deizon.system_barbershop.domain.dtos.BarbeariaDTO;
import com.deizon.system_barbershop.domain.models.Barbearia;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BarbeariaDTOMapper implements Function<Barbearia, BarbeariaDTO> {
    @Override
    public BarbeariaDTO apply(Barbearia barbearia) {
        return new BarbeariaDTO(
                barbearia.getId(),
                barbearia.getNome(),
                barbearia.getCnpj(),
                barbearia.getHorarios()
        );
    }
}
