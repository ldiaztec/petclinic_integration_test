package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import java.util.List;

public interface VetService {
    List<Vet> findAll();
    Vet create(Vet vet);
    void delete(Integer id);
}