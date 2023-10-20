package com.deizon.system_barbershop.domain.services.DTOMapper;

import com.deizon.system_barbershop.domain.dtos.HorarioDTO;
import com.deizon.system_barbershop.domain.models.Horario;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class HorarioDTOMapper implements Function<Horario, HorarioDTO> {

    @Override
    public HorarioDTO apply(Horario horario) {
        return new HorarioDTO(
                horario.getId(),
                horario.getHorarioInicial(),
                horario.getHorarioFinal(),
                horario.getReserva()
        );
    }
}
