package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.ReservaDTO;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.deizon.system_barbershop.domain.repositories.ReservaRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.ReservaDTOMapper;
import com.deizon.system_barbershop.domain.services.exceptions.ExistingFieldException;
import com.deizon.system_barbershop.domain.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
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

    @Override
    public List<ReservaDTO> findAll() {
        var reservas = reservaRepository.findAll()
                .stream()
                .map(reservaDTOMapper)
                .collect(Collectors.toList());
        return reservas;
    }

    @Override
    public ReservaDTO findByID(UUID id) {
        var reserva = reservaRepository.findById(id)
                .map(reservaDTOMapper);
        return reserva.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Reserva addResource(ReservaDTO reservaDTO) {
        try {
            var reserva = new Reserva();
            BeanUtils.copyProperties(reservaDTO, reserva);
            return reservaRepository.save(reserva);
        } catch (DataIntegrityViolationException error) {
            throw new ExistingFieldException("O horário já está cadastrado em outra reserva.");
        }
    }

    @Override
    public void remResource(UUID id) {
        var reserva = reservaRepository.findById(id);
        if (reserva.isPresent()) {
            reservaRepository.delete(reserva.get());
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public Reserva updateResource(UUID id, ReservaDTO newReserva) {
        var reserva = reservaRepository.getReferenceById(id);
        try {
            updateDataResource(reserva, newReserva);
            return reservaRepository.save(reserva);
        } catch (EntityNotFoundException error) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public void updateDataResource(Reserva oldResource, ReservaDTO newResource) {
        oldResource.setCliente(newResource.getCliente());
        oldResource.setHorario(newResource.getHorario());
    }
}
