package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
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
        Vet vet = toEntity(vetDTO);
        return toDTO(vetRepository.save(vet));
    }

    @Override
    public VetDTO update(VetDTO vetDTO) {
        Vet vet = toEntity(vetDTO);
        return toDTO(vetRepository.save(vet));
    }

    @Override
    public void delete(Integer id) throws VetNotFoundException {
        VetDTO vet = findById(id);
        vetRepository.deleteById(vet.getId());
    }

    @Override
    public VetDTO findById(Integer id) throws VetNotFoundException {
        Optional<Vet> vet = vetRepository.findById(id);
        if (!vet.isPresent())
            throw new VetNotFoundException("Vet not found...!");
        return toDTO(vet.get());
    }

    @Override
    public List<VetDTO> findAll() {
        return vetRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private VetDTO toDTO(Vet vet) {
        return VetDTO.builder()
                .id(vet.getId())
                .firstName(vet.getFirstName())
                .lastName(vet.getLastName())
                .build();
    }

    private Vet toEntity(VetDTO dto) {
        Vet vet = new Vet();
        vet.setId(dto.getId());
        vet.setFirstName(dto.getFirstName());
        vet.setLastName(dto.getLastName());
        return vet;
    }
}