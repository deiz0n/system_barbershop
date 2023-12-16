package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.ClienteDTO;
import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.repositories.ClienteRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.ClienteDTOMapper;
import com.deizon.system_barbershop.domain.services.exceptions.DataIntegrityException;
import com.deizon.system_barbershop.domain.services.exceptions.ExistingFieldException;
import com.deizon.system_barbershop.domain.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    //Lista todos os clientes
    @Override
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteDTOMapper)
                .collect(Collectors.toList());
    }

    //Retorna o cliente comforme o id
    @Override
    public ClienteDTO findByID(UUID id) {
        var cliente = clienteRepository.findById(id)
                .map(clienteDTOMapper);
        return cliente.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    //Adiciona um cliente
    @Override
    public Cliente addResource(ClienteDTO clienteDTO) {
        try {
            var cliente = new Cliente();
            newDataValidation(clienteDTO);
            BeanUtils.copyProperties(clienteDTO, cliente, "id");
            return clienteRepository.save(cliente);
        } catch (ExistingFieldException error) {
            throw new ExistingFieldException(error.getMessage());
        }
    }

    //Remove um cliente conforme id
    @Override
    public void remResource(UUID id) {
        var cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            clienteRepository.delete(cliente.get());
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    //Atualiza os dados do cliente
    @Override
    public Cliente updateResource(UUID id, ClienteDTO newCliente) {
        var oldCliente = clienteRepository.getReferenceById(id);
        try {
            newDataValidation(newCliente);
            updateDataResource(oldCliente, newCliente);
            return clienteRepository.save(oldCliente);
        } catch (EntityNotFoundException error) {
            throw new ResourceNotFoundException(id);
        } catch (ExistingFieldException error) {
            throw new ExistingFieldException(error.getMessage());
        }
    }

    @Override
    public void updateDataResource(Cliente oldResource, ClienteDTO newResource) {
        oldResource.setNome(newResource.getNome());
        oldResource.setCpf(newResource.getCpf());
        oldResource.setTelefone(newResource.getTelefone());
        oldResource.setEmail(newResource.getEmail());
    }

    //Verifica se os dados inseridos são válidos
    @Override
    public boolean newDataValidation(ClienteDTO newCliente) {
        if (clienteRepository.existsCPF().contains(newCliente.getCpf())) {
            throw new ExistingFieldException("CPF já cadastrado");
        } else if (clienteRepository.existsEmail().contains(newCliente.getEmail())) {
            throw new ExistingFieldException("Email já cadastrado");
        } else if (clienteRepository.existsTelefone().contains(newCliente.getTelefone())) {
            throw new ExistingFieldException("Telefone já cadastrado");
        } else {
            return true;
        }
    }

}
