package com.deizon.system_barbershop.api.controllers;

import com.deizon.system_barbershop.domain.dtos.ReservaDTO;
import com.deizon.system_barbershop.domain.services.ReservaService;
import com.deizon.system_barbershop.domain.services.exceptions.ExistingFieldException;
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

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> getReservas() {
        var reservas = reservaService.findAll();
        return ResponseEntity.ok().body(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> getReserva(@PathVariable UUID id) {
        var reserva = reservaService.findByID(id);
        return ResponseEntity.ok().body(reserva);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> createReserva(@RequestBody @Valid ReservaDTO newReserva) {
        try {
            var reserva = reservaService.addResource(newReserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
        } catch (ExistingFieldException error) {
            return ResponseEntity.badRequest().body("O horário informado já está cadastrado em outra reserva.");
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserva(@PathVariable UUID id) {
        reservaService.remResource(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReserva(@PathVariable UUID id, @RequestBody @Valid ReservaDTO newReserva) {
        var reserva = reservaService.updateResource(id, newReserva);
        return ResponseEntity.ok().body(reserva);
    }

}
