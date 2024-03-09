package com.deizon.system_barbershop.domain.services.DTOMapper;

import com.deizon.system_barbershop.domain.dtos.ClienteDTO;
import com.deizon.system_barbershop.domain.models.Cliente;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ClienteDTOMapper implements Function<Cliente, ClienteDTO> {

    @Override
    public ClienteDTO apply(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getReservas()
        );
    }
}
