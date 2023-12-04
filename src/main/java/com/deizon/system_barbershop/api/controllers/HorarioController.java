package com.deizon.system_barbershop.api.controllers;

import com.deizon.system_barbershop.domain.dtos.HorarioDTO;
import com.deizon.system_barbershop.domain.repositories.HorarioRepository;
import com.deizon.system_barbershop.domain.services.HorarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private HorarioRepository horarioRepository;

    @GetMapping
    public ResponseEntity<List<HorarioDTO>> getHorarios() {
        var horarios = horarioService.findAll();
        return ResponseEntity.ok().body(horarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioDTO> getHorario(@PathVariable UUID id) {
        var horario = horarioService.findByID(id);
        return ResponseEntity.ok().body(horario);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> createHorario(@RequestBody @Valid HorarioDTO newHorario) {
        if (horarioRepository.existsHorario().contains(newHorario.getHorarioInicial()) && horarioRepository.existsHorario().contains(newHorario.getHorarioFinal())) {
            return ResponseEntity.badRequest().body("Horário já cadastrado");
        } else {
            var horario = horarioService.addResource(newHorario);
            return ResponseEntity.status(HttpStatus.CREATED).body(horario);
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHorario(@PathVariable UUID id) {
        horarioService.remResource(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateHorario(@PathVariable UUID id,
                                           @RequestBody @Valid HorarioDTO newHorario) {
        var horario = horarioService.updateResource(id, newHorario);
        return ResponseEntity.ok().body(horario);
    }

}
