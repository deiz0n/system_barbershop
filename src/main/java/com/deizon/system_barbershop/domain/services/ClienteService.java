package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.ClienteDTO;
import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.repositories.ClienteRepository;
import com.deizon.system_barbershop.domain.repositories.ReservaRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.ClienteDTOMapper;
import com.deizon.system_barbershop.domain.services.exceptions.DataIntegrityException;
import com.deizon.system_barbershop.domain.services.exceptions.ExistingFieldException;
import com.deizon.system_barbershop.domain.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClienteService implements ServiceCRUD<ClienteDTO, Cliente> {

    private ReservaRepository reservaRepository;

    private ClienteRepository clienteRepository;

    private ClienteDTOMapper clienteDTOMapper;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ClienteDTOMapper clienteDTOMapper, ReservaRepository reservaRepository) {
        this.clienteRepository = clienteRepository;
        this.clienteDTOMapper = clienteDTOMapper;
        this.reservaRepository = reservaRepository;
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
        var cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO, cliente);
        dataValidation(cliente);
        return clienteRepository.save(cliente);
    }

    //Remove um cliente conforme id
    @Override
    public void remResource(UUID id) {
        var cliente = clienteRepository.findById(id);
        if (!cliente.isPresent())
            throw new ResourceNotFoundException(id);
        dataValidation(cliente.get());
        clienteRepository.delete(cliente.get());
    }

    //Atualiza os dados do cliente
    @Override
    public Cliente updateResource(UUID id, ClienteDTO newCliente) {
        var oldCliente = clienteRepository.getReferenceById(id);
        try {
            updateDataResource(oldCliente, newCliente);
            dataValidation(oldCliente);
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

    //Verifica se os dados inseridos são válidos
    @Override
    public void dataValidation(Cliente newCliente) {
        if (reservaRepository.findByCliente(newCliente).isPresent())
            throw new DataIntegrityException("O cliente não pode ser excluído pois está vinculado a uma reserva.");
        var clienteByCpf = clienteRepository.findByCpf(newCliente.getCpf());
        if (clienteByCpf.isPresent() && !clienteByCpf.get().getId().equals(newCliente.getId()))
            throw new ExistingFieldException("CPF já cadastrado");
        var clienteByEmail = clienteRepository.findByEmail(newCliente.getEmail());
        if (clienteByEmail.isPresent() && !clienteByEmail.get().getId().equals(newCliente.getId()))
            throw new ExistingFieldException("Email já cadastrado");
        var clienteByTelefone = clienteRepository.findByTelefone(newCliente.getTelefone());
        if (clienteByTelefone.isPresent() && !clienteByTelefone.get().getId().equals(newCliente.getId()))
            throw new ExistingFieldException("Telefone já cadastrado");
    }

}
