package com.deizon.system_barbershop.api.controllers;

import com.deizon.system_barbershop.domain.dtos.BarbeariaDTO;
import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.deizon.system_barbershop.domain.services.BarbeariaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/barbearias")
public class BarbeariaController {

    private BarbeariaService barbeariaService;

    @Autowired
    public BarbeariaController(BarbeariaService barbeariaService) {
        this.barbeariaService = barbeariaService;
    }

    //Retonar todas as barbeároas
    @GetMapping
    public ResponseEntity<List<BarbeariaDTO>> getBarbearias() {
        var barbearias = barbeariaService.findAll();
        return ResponseEntity.ok().body(barbearias);
    }

    //Retorna uma barbeária comforme id
    @GetMapping("/{id}")
    public ResponseEntity<BarbeariaDTO> getBarbearia(@PathVariable UUID id) {
        var barbearia = barbeariaService.findByID(id);
        return ResponseEntity.ok().body(barbearia);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Barbearia> getBarbeariaByEmail(@PathVariable String email) {
        var obj = barbeariaService.findByEmail(email);
        return ResponseEntity.ok().body(obj);
    }

    //Cria uma nova barbeária
    @Transactional
    @PostMapping
    public ResponseEntity<Barbearia> createBarbearia(@RequestBody @Valid BarbeariaDTO newBarbearia) {
        var barbearia = barbeariaService.addResource(newBarbearia);
        return ResponseEntity.status(HttpStatus.CREATED).body(barbearia);
    }

    //Deleta uma barbeária comforme id
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBarbearia(@PathVariable UUID id) {
        barbeariaService.remResource(id);
        return ResponseEntity.noContent().build();
    }

    //Atualiza os dados da barbeária comforme id
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Barbearia> updateBarbearia(@PathVariable UUID id,
                                             @RequestBody @Valid BarbeariaDTO newBarbearia) {
        var barbearia = barbeariaService.updateResource(id, newBarbearia);
        return ResponseEntity.ok().body(barbearia);
    }

}
