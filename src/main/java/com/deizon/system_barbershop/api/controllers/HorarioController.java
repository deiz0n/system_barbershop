package com.deizon.system_barbershop.api.controllers;

import com.deizon.system_barbershop.domain.dtos.HorarioDTO;
import com.deizon.system_barbershop.domain.models.Horario;
import com.deizon.system_barbershop.domain.services.HorarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    //Retonar todos os horários
    @GetMapping
    public ResponseEntity<List<HorarioDTO>> getHorarios() {
        var horarios = horarioService.findAll();
        return ResponseEntity.ok().body(horarios);
    }

    //Retorna um horário comforme id
    @GetMapping("/{id}")
    public ResponseEntity<HorarioDTO> getHorario(@PathVariable UUID id) {
        var horario = horarioService.findByID(id);
        return ResponseEntity.ok().body(horario);
    }

    //Cria um novo horário
    @Transactional
    @PostMapping
    public ResponseEntity<?> createHorario(@RequestBody @Valid HorarioDTO newHorario) {
        var horario = horarioService.addResource(newHorario);
        return ResponseEntity.status(HttpStatus.CREATED).body(horario);
    }

    //Deleta um horário comforme id
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHorario(@PathVariable UUID id) {
        horarioService.remResource(id);
        return ResponseEntity.noContent().build();
    }

    //Atualiza os dados do horário comforme id
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Horario> updateHorario(@PathVariable UUID id,
                                                 @RequestBody @Valid HorarioDTO newHorario) {
        var horario = horarioService.updateResource(id, newHorario);
        return ResponseEntity.ok().body(horario);
    }

}
