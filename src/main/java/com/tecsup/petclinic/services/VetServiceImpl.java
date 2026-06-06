package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.PetNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VetServiceImpl implements VetService {

    private VetRepository vetRepository;

    public VetServiceImpl(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public VetDTO create(VetDTO vetDTO) {
        Vet vet = mapToEntity(vetDTO);
        Vet saved = vetRepository.save(vet);
        return mapToDto(saved);
    }

    @Override
    public VetDTO update(VetDTO vetDTO) {
        Vet vet = mapToEntity(vetDTO);
        Vet saved = vetRepository.save(vet);
        return mapToDto(saved);
    }

    @Override
    public void delete(Integer id) throws PetNotFoundException {
        VetDTO vet = findById(id);
        vetRepository.delete(mapToEntity(vet));
    }

    @Override
    public VetDTO findById(Integer id) throws PetNotFoundException {
        Optional<Vet> vet = vetRepository.findById(id);
        if (!vet.isPresent())
            throw new PetNotFoundException("Vet not found with id: " + id);
        return mapToDto(vet.get());
    }

    @Override
    public List<VetDTO> findAll() {
        return vetRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private VetDTO mapToDto(Vet vet) {
        return VetDTO.builder()
                .id(vet.getId())
                .firstName(vet.getFirstName())
                .lastName(vet.getLastName())
                .build();
    }

    private Vet mapToEntity(VetDTO dto) {
        Vet vet = new Vet();
        vet.setId(dto.getId());
        vet.setFirstName(dto.getFirstName());
        vet.setLastName(dto.getLastName());
        return vet;
    }
}