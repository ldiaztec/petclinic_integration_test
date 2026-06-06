package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.PetNotFoundException;

import java.util.List;

public interface SpecialtyService {

    SpecialtyDTO create(SpecialtyDTO specialtyDTO);

    SpecialtyDTO update(SpecialtyDTO specialtyDTO);

    void delete(Integer id) throws PetNotFoundException;

    SpecialtyDTO findById(Integer id) throws PetNotFoundException;

    List<SpecialtyDTO> findAll();
}