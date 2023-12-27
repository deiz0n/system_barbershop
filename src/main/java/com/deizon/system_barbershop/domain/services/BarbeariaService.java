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

    //Lista todas as barbeárias
    @Override
    public List<BarbeariaDTO> findAll() {
        var barbearias = barbeariaRepository.findAll()
                .stream()
                .map(barbeariaDTOMapper)
                .collect(Collectors.toList());
        return barbearias;
    }

    //Lista uma barbeária comforme id
    @Override
    public BarbeariaDTO findByID(UUID id) {
        var barbearia = barbeariaRepository.findById(id)
                .map(barbeariaDTOMapper);
        return barbearia.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    //Adiciona uma barbeária
    @Override
    public Barbearia addResource(BarbeariaDTO barbeariaDTO) {
        try {
            var barbearia = new Barbearia();
            newDataValidation(barbeariaDTO);
            BeanUtils.copyProperties(barbeariaDTO, barbearia, "id");
            return barbeariaRepository.save(barbearia);
        } catch (ExistingFieldException error) {
            throw new ExistingFieldException(error.getMessage());
        }
    }

    //Remove uma barbeária comforme id
    @Override
    public void remResource(UUID id) {
        var barbearia = barbeariaRepository.findById(id);
        if (!barbearia.isPresent())
            throw new ResourceNotFoundException(id);
        barbeariaRepository.delete(barbearia.get());
    }

    //Atualiza os dados da barbearia
    @Override
    public Barbearia updateResource(UUID id, BarbeariaDTO barbeariaDTO) {
        var oldCliente = barbeariaRepository.getReferenceById(id);
        try {
            newDataValidation(barbeariaDTO);
            updateDataResource(oldCliente, barbeariaDTO);
            return barbeariaRepository.save(oldCliente);
        } catch (EntityNotFoundException error) {
            throw new ResourceNotFoundException(id);
        } catch (ExistingFieldException error) {
            throw new ExistingFieldException(error.getMessage());
        }
    }

    @Override
    public void updateDataResource(Barbearia oldResource, BarbeariaDTO newResource) {
        oldResource.setNome(newResource.getNome());
        oldResource.setCnpj(newResource.getCnpj());
    }

    //Verifica se os dados inseridos são válidos
    @Override
    public boolean newDataValidation(BarbeariaDTO newBarbearia) {
        if (barbeariaRepository.getBarbeariaByNome(newBarbearia.getNome()).isPresent())
            throw new ExistingFieldException("Nome já cadastrado");
        if (barbeariaRepository.getBarbeariaByCnpj(newBarbearia.getCnpj()).isPresent())
            throw new ExistingFieldException("CNPJ já cadastrado");
        return true;
    }
}
