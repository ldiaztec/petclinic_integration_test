package com.tecsup.petclinic.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;

    public VetServiceImpl(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public VetDTO create(VetDTO dto) {
        Vet vet = new Vet();
        vet.setFirstName(dto.getFirstName());
        vet.setLastName(dto.getLastName());
        return toDTO(vetRepository.save(vet));
    }

    @Override
    public VetDTO update(VetDTO dto) {
        Vet vet = new Vet();
        vet.setId(dto.getId());
        vet.setFirstName(dto.getFirstName());
        vet.setLastName(dto.getLastName());
        return toDTO(vetRepository.save(vet));
    }

    @Override
    public void delete(Integer id) throws VetNotFoundException {
        VetDTO dto = findById(id);
        Vet vet = new Vet();
        vet.setId(dto.getId());
        vet.setFirstName(dto.getFirstName());
        vet.setLastName(dto.getLastName());
        vetRepository.delete(vet);
    }

    @Override
    public VetDTO findById(Integer id) throws VetNotFoundException {
        Optional<Vet> vet = vetRepository.findById(id);
        if (!vet.isPresent()) throw new VetNotFoundException("Vet not found: " + id);
        return toDTO(vet.get());
    }

    @Override
    public List<VetDTO> findAll() {
        return vetRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private VetDTO toDTO(Vet v) {
        return VetDTO.builder()
                .id(v.getId())
                .firstName(v.getFirstName())
                .lastName(v.getLastName())
                .build();
    }
}