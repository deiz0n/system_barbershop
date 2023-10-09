package com.deizon.system_barbershop.api.controllers;

import com.deizon.system_barbershop.domain.dtos.ClienteDTO;
import com.deizon.system_barbershop.domain.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        var clientes = clienteService.findAll();
        return ResponseEntity.ok().body(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCliente(@PathVariable UUID id) {
        var cliente = clienteService.findByID(id);
        if (cliente != null) {
            return ResponseEntity.ok().body(cliente);
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping
    public ResponseEntity<?> addCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        var cliente = clienteService.addResource(clienteDTO);
        return ResponseEntity.ok().body(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable UUID id) {
        clienteService.remResource(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable UUID id,
                                           @RequestBody @Valid ClienteDTO clienteDTO) {
        var cliente = clienteService.updateResource(id, clienteDTO);
        return ResponseEntity.ok().body(cliente);
    }


}
