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

    private SpecialtyRepository specialtyRepository;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public SpecialtyDTO create(SpecialtyDTO dto) {
        return toDTO(specialtyRepository.save(toEntity(dto)));
    }

    @Override
    public SpecialtyDTO update(SpecialtyDTO dto) {
        return toDTO(specialtyRepository.save(toEntity(dto)));
    }

    @Override
    public void delete(Integer id) throws SpecialtyNotFoundException {
        findById(id);
        specialtyRepository.deleteById(id);
    }

    @Override
    public SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        if (!specialty.isPresent())
            throw new SpecialtyNotFoundException("Specialty not found...!");
        return toDTO(specialty.get());
    }

    @Override
    public List<SpecialtyDTO> findAll() {
        return specialtyRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private SpecialtyDTO toDTO(Specialty s) {
        return SpecialtyDTO.builder()
                .id(s.getId())
                .name(s.getName())
                .build();
    }

    private Specialty toEntity(SpecialtyDTO dto) {
        Specialty s = new Specialty();
        s.setId(dto.getId());
        s.setName(dto.getName());
        return s;
    }
}