package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.HorarioDTO;
import com.deizon.system_barbershop.domain.models.Horario;
import com.deizon.system_barbershop.domain.repositories.HorarioRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.HorarioDTOMapper;
import com.deizon.system_barbershop.domain.services.exceptions.ExistingFieldException;
import com.deizon.system_barbershop.domain.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HorarioService implements ServiceCRUD<HorarioDTO, Horario> {

    private HorarioRepository horarioRepository;

    private HorarioDTOMapper horarioDTOMapper;

    @Autowired
    public HorarioService(HorarioRepository horarioRepository, HorarioDTOMapper horarioDTOMapper) {
        this.horarioRepository = horarioRepository;
        this.horarioDTOMapper = horarioDTOMapper;
    }

    @Override
    public List<HorarioDTO> findAll() {
        var horarios = horarioRepository.findAll()
                .stream()
                .map(horarioDTOMapper)
                .collect(Collectors.toList());
        return horarios;
    }

    @Override
    public HorarioDTO findByID(UUID id) {
        var horario = horarioRepository.findById(id)
                .map(horarioDTOMapper);
        return horario.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Horario addResource(HorarioDTO horarioDTO) {
        var horario = new Horario();
        BeanUtils.copyProperties(horarioDTO, horario, "id");
        return horarioRepository.save(horario);
    }

    @Override
    public void remResource(UUID id) {
        var horario = horarioRepository.findById(id);
        if (horario.isPresent()) {
            horarioRepository.delete(horario.get());
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public Horario updateResource(UUID id, HorarioDTO newHorario) {
        var oldHorario = horarioRepository.getReferenceById(id);
        try {
            updateDataResource(oldHorario, newHorario);
            //BeanUtils.copyProperties(newHorario, oldHorario);
            return horarioRepository.save(oldHorario);
        } catch (EntityNotFoundException error) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public void updateDataResource(Horario oldResource, HorarioDTO newResource) {
        oldResource.setHorarioInicial(newResource.getHorarioInicial());
        oldResource.setHorarioFinal(newResource.getHorarioFinal());
    }
}
