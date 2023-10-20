package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.ClienteDTO;
import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.repositories.ClienteRepository;
import com.deizon.system_barbershop.domain.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClienteService implements ServiceCRUD<ClienteDTO, Cliente> {


    private ClienteRepository clienteRepository;

    private ClienteDTOMapper clienteDTOMapper;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ClienteDTOMapper clienteDTOMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteDTOMapper = clienteDTOMapper;
    }

    @Override
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO findByID(UUID id) {
        var cliente = clienteRepository.findById(id)
                .map(clienteDTOMapper);
        return cliente.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Cliente addResource(ClienteDTO clienteDTO) {
        var cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO, cliente, "id");
        return clienteRepository.save(cliente);
    }

    @Override
    public void remResource(UUID id) {
        var cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            clienteRepository.delete(cliente.get());
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public Cliente updateResource(UUID id, ClienteDTO newCliente) {
        var oldCliente = clienteRepository.getReferenceById(id);
        try {
            updateDataResource(oldCliente, newCliente);
            return clienteRepository.save(oldCliente);
        } catch (EntityNotFoundException error) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public void updateDataResource(Cliente oldResource, ClienteDTO newResource) {
        oldResource.setNome(newResource.getNome());
        oldResource.setCpf(newResource.getCpf());
        oldResource.setTelefone(newResource.getTelefone());
        oldResource.setEmail(newResource.getEmail());
    }
}
