package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.HorarioDTO;
import com.deizon.system_barbershop.domain.models.Horario;
import com.deizon.system_barbershop.domain.repositories.HorarioRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.HorarioDTOMapper;
import com.deizon.system_barbershop.domain.services.exceptions.ArgumentNotValidException;
import com.deizon.system_barbershop.domain.services.exceptions.ExistingFieldException;
import com.deizon.system_barbershop.domain.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
        dataValidation(horario);
        return horarioRepository.save(horario);
    }

    //Remove um horário comforme id
    @Override
    public void remResource(UUID id) {
        var horario = horarioRepository.findById(id);
        if (!horario.isPresent())
            throw new ResourceNotFoundException(id);
        horarioRepository.delete(horario.get());
    }

    //Atualiza os dados do horário
    @Override
    public Horario updateResource(UUID id, HorarioDTO newHorario) {
        var oldHorario = horarioRepository.getReferenceById(id);
        try {
            updateDataResource(oldHorario, newHorario);
            dataValidation(oldHorario);
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

    //Verifica se os dados inseridos são válidos
    public void dataValidation(Horario newHorario) {
        var horarioByBarbearia = horarioRepository.findByBarbearia(newHorario.getBarbearia());
        if (horarioByBarbearia.isPresent() && !horarioByBarbearia.get().getId().equals(newHorario.getId())) { //Verifica se o horário está vinculado a uma barbeária
            throw new ExistingFieldException("Horário já cadastrado");
        }
        if (newHorario.getHorarioInicial().isAfter(newHorario.getHorarioFinal())) { //Verifica se o horário inicial é posterior ao final
            throw new ArgumentNotValidException("O horário inicial não pode ser posteior ao horário final.");
        }
        var duration = Duration.between(newHorario.getHorarioInicial(), newHorario.getHorarioFinal()).toMinutes();
        if (duration < 20)  //Verifica se a diferença entre horários é menor que 20 minutos
            throw new ArgumentNotValidException("Intervalo de tempo muito curto. Tente novamente");
    }
}
