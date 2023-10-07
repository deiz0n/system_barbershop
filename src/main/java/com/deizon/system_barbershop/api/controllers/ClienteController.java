package com.deizon.system_barbershop.api.controllers;

import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
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

}
