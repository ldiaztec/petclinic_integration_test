package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public OwnerDTO create(OwnerDTO dto) {
        Owner owner = new Owner();
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setAddress(dto.getAddress());
        owner.setCity(dto.getCity());
        owner.setTelephone(dto.getTelephone());
        return toDTO(ownerRepository.save(owner));
    }

    @Override
    public OwnerDTO update(OwnerDTO dto) {
        Owner owner = new Owner();
        owner.setId(dto.getId());
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setAddress(dto.getAddress());
        owner.setCity(dto.getCity());
        owner.setTelephone(dto.getTelephone());
        return toDTO(ownerRepository.save(owner));
    }

    @Override
    public void delete(Long id) throws OwnerNotFoundException {
        OwnerDTO dto = findById(id);
        Owner owner = new Owner();
        owner.setId(dto.getId());
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setAddress(dto.getAddress());
        owner.setCity(dto.getCity());
        owner.setTelephone(dto.getTelephone());
        ownerRepository.delete(owner);
    }

    @Override
    public OwnerDTO findById(Long id) throws OwnerNotFoundException {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (!owner.isPresent()) throw new OwnerNotFoundException("Owner not found: " + id);
        return toDTO(owner.get());
    }

    @Override
    public List<OwnerDTO> findAll() {
        return ownerRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private OwnerDTO toDTO(Owner o) {
        return OwnerDTO.builder()
                .id(o.getId())
                .firstName(o.getFirstName())
                .lastName(o.getLastName())
                .address(o.getAddress())
                .city(o.getCity())
                .telephone(o.getTelephone())
                .build();
    }
}