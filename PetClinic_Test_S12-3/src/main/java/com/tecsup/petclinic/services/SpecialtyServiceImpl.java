package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public SpecialtyDTO create(SpecialtyDTO dto) {
        Specialty specialty = new Specialty();
        specialty.setName(dto.getName());
        return toDTO(specialtyRepository.save(specialty));
    }

    @Override
    public SpecialtyDTO update(SpecialtyDTO dto) {
        Specialty specialty = new Specialty();
        specialty.setId(dto.getId());
        specialty.setName(dto.getName());
        return toDTO(specialtyRepository.save(specialty));
    }

    @Override
    public void delete(Integer id) throws SpecialtyNotFoundException {
        SpecialtyDTO dto = findById(id);
        Specialty specialty = new Specialty();
        specialty.setId(dto.getId());
        specialty.setName(dto.getName());
        specialtyRepository.delete(specialty);
    }

    @Override
    public SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        if (!specialty.isPresent()) throw new SpecialtyNotFoundException("Specialty not found: " + id);
        return toDTO(specialty.get());
    }

    @Override
    public List<SpecialtyDTO> findAll() {
        return specialtyRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private SpecialtyDTO toDTO(Specialty s) {
        return SpecialtyDTO.builder()
                .id(s.getId())
                .name(s.getName())
                .build();
    }
}