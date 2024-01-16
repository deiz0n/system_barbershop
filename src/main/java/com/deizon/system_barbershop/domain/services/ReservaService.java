package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.ReservaDTO;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.deizon.system_barbershop.domain.repositories.ReservaRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.ReservaDTOMapper;
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
public class ReservaService implements ServiceCRUD<ReservaDTO, Reserva>{

    private ReservaRepository reservaRepository;

    private ReservaDTOMapper reservaDTOMapper;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository, ReservaDTOMapper reservaDTOMapper) {
        this.reservaRepository = reservaRepository;
        this.reservaDTOMapper = reservaDTOMapper;
    }

    //Lista todas as reservas
    @Override
    public List<ReservaDTO> findAll() {
        var reservas = reservaRepository.findAll()
                .stream()
                .map(reservaDTOMapper)
                .collect(Collectors.toList());
        return reservas;
    }

    //Retorna a reserva comforme id
    @Override
    public ReservaDTO findByID(UUID id) {
        var reserva = reservaRepository.findById(id)
                .map(reservaDTOMapper);
        return reserva.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    //Adiciona uma reserva
    @Override
    public Reserva addResource(ReservaDTO reservaDTO) {
        var reserva = new Reserva();
        BeanUtils.copyProperties(reservaDTO, reserva);
        dataValidation(reserva);
        return reservaRepository.save(reserva);
    }

    //Remove uma reserva comforme id
    @Override
    public void remResource(UUID id) {
        var reserva = reservaRepository.findById(id);
        if (!reserva.isPresent())
            throw new ResourceNotFoundException(id);
        reservaRepository.deleteById(reserva.get().getId());
    }

    //Atualiza os dados da reserva
    @Override
    public Reserva updateResource(UUID id, ReservaDTO newReserva) {
        var reserva = reservaRepository.getReferenceById(id);
        try {
            BeanUtils.copyProperties(newReserva, reserva, "id");
            dataValidation(reserva);
            return reservaRepository.save(reserva);
        } catch (EntityNotFoundException error) {
            throw new ResourceNotFoundException(id);
        }
    }

    //Verifica se os dados inseridos são válidos
    @Override
    public void dataValidation(Reserva newReserva) {
        var reservaByHorario = reservaRepository.findFirstByHorario(newReserva.getHorario());
        if (reservaByHorario.isPresent())
            throw new DataIntegrityException("O horário já está vinculado a uma reserva");
    }
}
