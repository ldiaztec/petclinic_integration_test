package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import java.util.List;

public interface SpecialtyService {
    List<Specialty> findAll();
    Specialty create(Specialty specialty);
    void delete(Integer id);
}