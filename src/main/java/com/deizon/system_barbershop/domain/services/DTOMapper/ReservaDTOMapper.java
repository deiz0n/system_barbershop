package com.deizon.system_barbershop.domain.services.DTOMapper;

import com.deizon.system_barbershop.domain.dtos.ReservaDTO;
import com.deizon.system_barbershop.domain.models.Reserva;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ReservaDTOMapper implements Function<Reserva, ReservaDTO> {

    @Override
    public ReservaDTO apply(Reserva reserva) {
        return new ReservaDTO(
                reserva.getId(),
                reserva.getHorario(),
                reserva.getNomeCliente(),
                reserva.getTelefoneCliente(),
                reserva.getStatus()
                );
    }
}
