package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.HorarioDTO;
import com.deizon.system_barbershop.domain.models.Horario;
import com.deizon.system_barbershop.domain.repositories.HorarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private HorarioDTOMapper horarioDTOMapper;

    public List<HorarioDTO> findAll() {
        var horarios = horarioRepository.findAll()
                .stream()
                .map(horarioDTOMapper)
                .collect(Collectors.toList());
        return horarios;
    }

    public HorarioDTO findByID(UUID id) {
        var horario = horarioRepository.findById(id)
                .map(horarioDTOMapper);
        return horario.get();
    }

    public Horario addResource(HorarioDTO horarioDTO) {
        var horario = new Horario();
        BeanUtils.copyProperties(horarioDTO, horario);
        return horarioRepository.save(horario);
    }

    public void remResource(UUID id) {
        horarioRepository.deleteById(id);
    }

    public Horario updateResource(UUID id, HorarioDTO newHorario) {
        var oldHorario = horarioRepository.getReferenceById(id);
        updateResourceData(oldHorario, newHorario);
        BeanUtils.copyProperties(newHorario, oldHorario);
        return horarioRepository.save(oldHorario);
    }

    public void updateResourceData(Horario oldHorario, HorarioDTO newHorario) {
        oldHorario.setHorarioInicial(newHorario.getHorarioInicial());
        oldHorario.setHorarioFinal(newHorario.getHorarioFinal());
    }

}
