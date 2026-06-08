package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository repository;

    @Override
    public List<Owner> findAll() {
        return repository.findAll();
    }

    @Override
    public Owner create(Owner owner) {
        return repository.save(owner);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}