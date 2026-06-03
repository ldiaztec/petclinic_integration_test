package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.PetNotFoundException;

import java.util.List;

public interface OwnerService {
    OwnerDTO create(OwnerDTO ownerDTO);
    OwnerDTO update(OwnerDTO ownerDTO);
    void delete(Long id) throws PetNotFoundException;
    OwnerDTO findById(Long id) throws PetNotFoundException;
    List<OwnerDTO> findAll();
}