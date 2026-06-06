package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.PetNotFoundException;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpecialtyServiceImpl implements SpecialtyService {

    private SpecialtyRepository specialtyRepository;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public SpecialtyDTO create(SpecialtyDTO specialtyDTO) {
        Specialty specialty = mapToEntity(specialtyDTO);
        Specialty saved = specialtyRepository.save(specialty);
        return mapToDto(saved);
    }

    @Override
    public SpecialtyDTO update(SpecialtyDTO specialtyDTO) {
        Specialty specialty = mapToEntity(specialtyDTO);
        Specialty saved = specialtyRepository.save(specialty);
        return mapToDto(saved);
    }

    @Override
    public void delete(Integer id) throws PetNotFoundException {
        SpecialtyDTO specialty = findById(id);
        specialtyRepository.delete(mapToEntity(specialty));
    }

    @Override
    public SpecialtyDTO findById(Integer id) throws PetNotFoundException {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        if (!specialty.isPresent())
            throw new PetNotFoundException("Specialty not found with id: " + id);
        return mapToDto(specialty.get());
    }

    @Override
    public List<SpecialtyDTO> findAll() {
        return specialtyRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private SpecialtyDTO mapToDto(Specialty specialty) {
        return SpecialtyDTO.builder()
                .id(specialty.getId())
                .name(specialty.getName())
                .build();
    }

    private Specialty mapToEntity(SpecialtyDTO dto) {
        Specialty specialty = new Specialty();
        specialty.setId(dto.getId());
        specialty.setName(dto.getName());
        return specialty;
    }
}