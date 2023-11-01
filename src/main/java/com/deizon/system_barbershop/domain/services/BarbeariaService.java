package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.BarbeariaDTO;
import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.repositories.BarbeariaRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.BarbeariaDTOMapper;
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
public class BarbeariaService implements ServiceCRUD<BarbeariaDTO, Barbearia> {

    private BarbeariaRepository barbeariaRepository;


    private BarbeariaDTOMapper barbeariaDTOMapper;

    @Autowired
    public BarbeariaService(BarbeariaRepository barbeariaRepository, BarbeariaDTOMapper barbeariaDTOMapper) {
        this.barbeariaRepository = barbeariaRepository;
        this.barbeariaDTOMapper = barbeariaDTOMapper;
    }

    @Override
    public List<BarbeariaDTO> findAll() {
        var barbearias = barbeariaRepository.findAll()
                .stream()
                .map(barbeariaDTOMapper)
                .collect(Collectors.toList());
        return barbearias;
    }

    @Override
    public BarbeariaDTO findByID(UUID id) {
        var barbearia = barbeariaRepository.findById(id)
                .map(barbeariaDTOMapper);
        return barbearia.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Barbearia addResource(BarbeariaDTO barbeariaDTO) {
        if (barbeariaRepository.existsNome().contains(barbeariaDTO.getNome())) {
            throw new ExistingFieldException("Nome já cadastrado");
        } else if (barbeariaRepository.existsCNPJ().contains(barbeariaDTO.getCnpj())) {
            throw new ExistingFieldException("CNPJ já cadastrado");
        } else {
            var barbearia = new Barbearia();
            BeanUtils.copyProperties(barbeariaDTO, barbearia, "id");
            return barbeariaRepository.save(barbearia);
        }
    }

    @Override
    public void remResource(UUID id) {
        var barbearia = barbeariaRepository.findById(id);
        if (barbearia.isPresent()) {
            barbeariaRepository.delete(barbearia.get());
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public Barbearia updateResource(UUID id, BarbeariaDTO barbeariaDTO) {
        var oldCliente = barbeariaRepository.getReferenceById(id);
        try {
            updateDataResource(oldCliente, barbeariaDTO);
            return barbeariaRepository.save(oldCliente);
        } catch (EntityNotFoundException error) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public void updateDataResource(Barbearia oldResource, BarbeariaDTO newResource) {
        oldResource.setNome(newResource.getNome());
        oldResource.setCnpj(newResource.getCnpj());
    }
}
