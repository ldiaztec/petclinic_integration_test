package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.NotFoundException;
import java.util.List;

public interface VetService {

    Vet create(Vet vet);

    Vet update(Vet vet);

    void delete(Integer id) throws NotFoundException;

    Vet findById(Integer id) throws NotFoundException;

    List<Vet> findByLastName(String lastName);

    List<Vet> findAll();
}