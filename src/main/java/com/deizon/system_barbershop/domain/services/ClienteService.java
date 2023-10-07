package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findByID(UUID id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }


}
