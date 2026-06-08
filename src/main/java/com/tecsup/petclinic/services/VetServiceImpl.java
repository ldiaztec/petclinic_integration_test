package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.repositories.VetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VetServiceImpl implements VetService {

    @Autowired
    private VetRepository repository;

    @Override
    public List<Vet> findAll() {
        return repository.findAll();
    }

    @Override
    public Vet create(Vet vet) {
        return repository.save(vet);
            }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}