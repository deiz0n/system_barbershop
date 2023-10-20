package com.deizon.system_barbershop.api.controllers;

import com.deizon.system_barbershop.domain.dtos.ClienteDTO;
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

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getClientes() {
        var clientes = clienteService.findAll();
        return ResponseEntity.ok().body(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCliente(@PathVariable UUID id) {
        var cliente = clienteService.findByID(id);
        return ResponseEntity.ok().body(cliente);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody @Valid ClienteDTO newCliente) {
        var cliente = clienteService.addResource(newCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable UUID id) {
        clienteService.remResource(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable UUID id,
                                           @RequestBody @Valid ClienteDTO newCliente) {
        var cliente = clienteService.updateResource(id, newCliente);
        return ResponseEntity.ok().body(cliente);
    }


}
