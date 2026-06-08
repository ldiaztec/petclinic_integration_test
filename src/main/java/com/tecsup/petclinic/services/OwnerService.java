package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import java.util.List;

public interface OwnerService {
    List<Owner> findAll();
    Owner create(Owner owner);
    void delete(Integer id);
}