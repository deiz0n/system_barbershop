package com.deizon.system_barbershop.api.controllers;

import com.deizon.system_barbershop.domain.dtos.ReservaDTO;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.deizon.system_barbershop.domain.services.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    //Retonar todas as reservas
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> getReservas() {
        var reservas = reservaService.findAll();
        return ResponseEntity.ok().body(reservas);
    }

    //Retorna uma reserva comforme id
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> getReserva(@PathVariable UUID id) {
        var reserva = reservaService.findByID(id);
        return ResponseEntity.ok().body(reserva);
    }

    //Cria uma nova reserva
    @Transactional
    @PostMapping
    public ResponseEntity<?> createReserva(@RequestBody @Valid ReservaDTO newReserva) {
        var reserva = reservaService.addResource(newReserva);
        //emailService.sendEmail(reserva.getId(), new Email());
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }
    
    //Deleta uma reserva comforme id
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserva(@PathVariable UUID id) {
        reservaService.remResource(id);
        return ResponseEntity.noContent().build();
    }

    //Atualiza os dados da reserva comforme id
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable UUID id, @RequestBody @Valid ReservaDTO newReserva) {
        var reserva = reservaService.updateResource(id, newReserva);
        return ResponseEntity.ok().body(reserva);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<Reserva> updateStatusReserva(@PathVariable UUID id, @RequestBody ReservaDTO newReserva) {
        var reserva = reservaService.updateResource(id, newReserva);
        return ResponseEntity.ok().body(reserva);
    }
}
