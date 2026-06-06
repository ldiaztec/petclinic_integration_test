package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.PetNotFoundException;

import java.util.List;

public interface VetService {

    VetDTO create(VetDTO vetDTO);

    VetDTO update(VetDTO vetDTO);

    void delete(Integer id) throws PetNotFoundException;

    VetDTO findById(Integer id) throws PetNotFoundException;

    List<VetDTO> findAll();
}