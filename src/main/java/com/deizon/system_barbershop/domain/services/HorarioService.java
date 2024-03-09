package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.HorarioDTO;
import com.deizon.system_barbershop.domain.models.Horario;
import com.deizon.system_barbershop.domain.repositories.HorarioRepository;
import com.deizon.system_barbershop.domain.repositories.ReservaRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.HorarioDTOMapper;
import com.deizon.system_barbershop.domain.services.exceptions.DataIntegrityException;
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

    private ReservaRepository reservaRepository;

    private HorarioRepository horarioRepository;

    private HorarioDTOMapper horarioDTOMapper;

    @Autowired
    public HorarioService(HorarioRepository horarioRepository, HorarioDTOMapper horarioDTOMapper, ReservaRepository reservaRepository) {
        this.horarioRepository = horarioRepository;
        this.horarioDTOMapper = horarioDTOMapper;
        this.reservaRepository = reservaRepository;
    }

    //Lista todos os horários
    @Override
    public List<HorarioDTO> findAll() {
        var horarios = horarioRepository.findAll()
                .stream()
                .map(horarioDTOMapper)
                .collect(Collectors.toList());
        return horarios;
    }

    //Retorna o horário comforme o id
    @Override
    public HorarioDTO findByID(UUID id) {
        var horario = horarioRepository.findById(id)
                .map(horarioDTOMapper);
        return horario.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    //Adiciona um horário
    @Override
    public Horario addResource(HorarioDTO horarioDTO) {
        var horario = new Horario();
        BeanUtils.copyProperties(horarioDTO, horario);
        //dataValidation(horario);
        return horarioRepository.save(horario);
    }

    //Remove um horário comforme id
    @Override
    public void remResource(UUID id) {
        var horario = horarioRepository.findById(id);
        if (!horario.isPresent())
            throw new ResourceNotFoundException(id);
        dataValidation(horario.get());
        horarioRepository.deleteById(horario.get().getId());
    }

    //Atualiza os dados do horário
    @Override
    public Horario updateResource(UUID id, HorarioDTO newHorario) {
        var oldHorario = horarioRepository.getReferenceById(id);
        try {
            BeanUtils.copyProperties(newHorario, oldHorario, "id");
            dataValidation(oldHorario);
            return horarioRepository.save(oldHorario);
        } catch (EntityNotFoundException error) {
            throw new ResourceNotFoundException(id);
        }
    }

    //Verifica se os dados inseridos são válidos
    @Override
    public void dataValidation(Horario newHorario) {
        if (reservaRepository.findFirstByHorario(newHorario).isPresent())
            throw new DataIntegrityException("O horário não pode ser excluído pois está vinculado a uma reserva");
    }
}
