package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Vet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VetRepository extends CrudRepository<Vet, Integer> {
    @Override
    List<Vet> findAll();
}