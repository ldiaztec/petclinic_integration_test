package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.NotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VetServiceImpl implements VetService {

    @Autowired
    private VetRepository vetRepository;

    @Override
    public Vet create(Vet vet) {
        return vetRepository.save(vet);
    }

    @Override
    public Vet update(Vet vet) {
        return vetRepository.save(vet);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        Vet vet = findById(id);
        vetRepository.delete(vet);
    }

    @Override
    public Vet findById(Integer id) throws NotFoundException {
        Optional<Vet> vet = vetRepository.findById(id);
        if (!vet.isPresent()) {
            throw new NotFoundException("Veterinario no encontrado");
        }
        return vet.get();
    }

    @Override
    public List<Vet> findByLastName(String lastName) {
        return vetRepository.findByLastName(lastName);
    }

    @Override
    public List<Vet> findAll() {
        return vetRepository.findAll();
    }
}