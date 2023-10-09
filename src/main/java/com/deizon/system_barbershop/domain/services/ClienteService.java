package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.ClienteDTO;
import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.repositories.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteDTOMapper clienteDTOMapper;

    public List<ClienteDTO> findAll() {
       return clienteRepository.findAll()
               .stream()
               .map(clienteDTOMapper)
               .collect(Collectors.toList());
    }

    public ClienteDTO findByID(UUID id) {
        var cliente = clienteRepository.findById(id)
                .map(clienteDTOMapper);
        return cliente.get();
    }

   public Cliente addResource(ClienteDTO clienteDTO) {
        var cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO, cliente, "id");
        return clienteRepository.save(cliente);
   }

   public void remResource(UUID id) {
        clienteRepository.deleteById(id);
   }

   public Cliente updateResource(UUID id, ClienteDTO newCliente) {
        var oldCliente = clienteRepository.getReferenceById(id);
        updateDataResource(oldCliente, newCliente);
        return clienteRepository.save(oldCliente);
   }


   public void updateDataResource(Cliente oldCliente, ClienteDTO newCliente) {
       if (oldCliente.getNome().isBlank()) {
           oldCliente.setNome(newCliente.getNome());
       }
       if (oldCliente.getCpf().isBlank()) {
           oldCliente.setCpf(newCliente.getCpf());
       }
       if (oldCliente.getTelefone().isBlank()) {
           oldCliente.setTelefone(newCliente.getTelefone());
       }
       if (oldCliente.getEmail().isBlank()) {
           oldCliente.setEmail(newCliente.getEmail());
       }
   }

}
