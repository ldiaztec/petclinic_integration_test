package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.PetNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    private OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    private OwnerDTO toDTO(Owner owner) {
        return OwnerDTO.builder()
                .id(owner.getId())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .address(owner.getAddress())
                .city(owner.getCity())
                .telephone(owner.getTelephone())
                .build();
    }

    private Owner toEntity(OwnerDTO dto) {
        Owner owner = new Owner();
        owner.setId(dto.getId());
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setAddress(dto.getAddress());
        owner.setCity(dto.getCity());
        owner.setTelephone(dto.getTelephone());
        return owner;
    }

    @Override
    public OwnerDTO create(OwnerDTO ownerDTO) {
        Owner saved = ownerRepository.save(toEntity(ownerDTO));
        return toDTO(saved);
    }

    @Override
    public OwnerDTO update(OwnerDTO ownerDTO) {
        Owner saved = ownerRepository.save(toEntity(ownerDTO));
        return toDTO(saved);
    }

    @Override
    public void delete(Long id) throws PetNotFoundException {
        OwnerDTO owner = findById(id);
        ownerRepository.delete(toEntity(owner));
    }

    @Override
    public OwnerDTO findById(Long id) throws PetNotFoundException {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (!owner.isPresent())
            throw new PetNotFoundException("Owner not found...!");
        return toDTO(owner.get());
    }

    @Override
    public List<OwnerDTO> findAll() {
        return ownerRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}