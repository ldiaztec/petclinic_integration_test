package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    @Autowired
    private SpecialtyRepository repository;

    @Override
    public List<Specialty> findAll() {
        return repository.findAll();
    }

    @Override
    public Specialty create(Specialty specialty) {
        return repository.save(specialty);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}