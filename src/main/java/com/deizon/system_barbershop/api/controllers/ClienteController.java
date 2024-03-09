package com.deizon.system_barbershop.api.controllers;

import com.deizon.system_barbershop.domain.dtos.ClienteDTO;
import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //Retonar todos os clientes
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getClientes() {
        var clientes = clienteService.findAll();
        return ResponseEntity.ok().body(clientes);
    }

    //Retorna um cliente comforme id
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable UUID id) {
        var cliente = clienteService.findByID(id);
        return ResponseEntity.ok().body(cliente);
    }

    //Cria um novo cliente
    @Transactional
    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody @Valid ClienteDTO newCliente) {
        var cliente = clienteService.addResource(newCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
    }

    //Deleta um cliente comforme id
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable UUID id) {
        clienteService.remResource(id);
        return ResponseEntity.noContent().build();
    }

    //Atualiza os dados do cliente comforme id
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable UUID id,
                                                 @RequestBody @Valid ClienteDTO newCliente) {
        var cliente = clienteService.updateResource(id, newCliente);
        return ResponseEntity.ok().body(cliente);
    }
}
