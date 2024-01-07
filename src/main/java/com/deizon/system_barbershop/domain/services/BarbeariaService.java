package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.dtos.BarbeariaDTO;
import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.repositories.BarbeariaRepository;
import com.deizon.system_barbershop.domain.services.DTOMapper.BarbeariaDTOMapper;
import com.deizon.system_barbershop.domain.services.exceptions.ExistingFieldException;
import com.deizon.system_barbershop.domain.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BarbeariaService implements ServiceCRUD<BarbeariaDTO, Barbearia> {

    private BarbeariaRepository barbeariaRepository;

    private BarbeariaDTOMapper barbeariaDTOMapper;

    private ModelMapper mapper;

    @Autowired
    public BarbeariaService(BarbeariaRepository barbeariaRepository, BarbeariaDTOMapper barbeariaDTOMapper, ModelMapper mapper) {
        this.barbeariaRepository = barbeariaRepository;
        this.barbeariaDTOMapper = barbeariaDTOMapper;
        this.mapper = mapper;
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
        var barbearia = new Barbearia();
        BeanUtils.copyProperties(barbeariaDTO, barbearia);
        dataValidation(barbearia);
        return barbeariaRepository.save(barbearia);
    }

    //Remove uma barbeária comforme id
    @Override
    public void remResource(UUID id) {
        var barbearia = barbeariaRepository.findById(id);
        if (!barbearia.isPresent())
            throw new ResourceNotFoundException(id);
        barbeariaRepository.deleteById(barbearia.get().getId());
    }

    //Atualiza os dados da barbearia
    @Override
    public Barbearia updateResource(UUID id, BarbeariaDTO barbeariaDTO) {
        findByID(id);
        barbeariaDTO.setId(id);
        var barbearia = mapper.map(barbeariaDTO, Barbearia.class);
        dataValidation(barbearia);
        return barbeariaRepository.save(barbearia);
    }

    @Override
    public void updateDataResource(Barbearia oldResource, BarbeariaDTO newResource) {
        oldResource.setNome(newResource.getNome());
        oldResource.setCnpj(newResource.getCnpj());
    }

    //Verifica se os dados inseridos são válidos
    @Override
    public void dataValidation(Barbearia newBarbeariaa) {
        var barbeariaByNome = barbeariaRepository.findByNome(newBarbeariaa.getNome());
        if (barbeariaByNome.isPresent() && !barbeariaByNome.get().getId().equals(newBarbeariaa.getId()))
            throw new ExistingFieldException("Nome já vinculado a outra barbeária");
        var barbeariaByCnpj = barbeariaRepository.findByCnpj(newBarbeariaa.getCnpj());
        if (barbeariaByCnpj.isPresent() && !barbeariaByCnpj.get().getId().equals(newBarbeariaa.getId()))
          throw new ExistingFieldException("CNPJ já vinculado a outra barbeária");
    }
}
