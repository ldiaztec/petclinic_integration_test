package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Integer> {
    @Override
    List<Owner> findAll();
}