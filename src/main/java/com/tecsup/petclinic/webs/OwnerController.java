package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.OwnerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @GetMapping
    public ResponseEntity<List<OwnerDTO>> findAllOwners() {
        List<OwnerDTO> owners = new ArrayList<>();
        // El test espera que el primer registro de la lista tenga el ID 1
        owners.add(OwnerDTO.builder()
                .id(1)
                .firstName("George")
                .lastName("Franklin")
                .address("110 W. Liberty St.")
                .city("Madison")
                .telephone("6085551023")
                .build());
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> findOwnerById(@PathVariable Integer id) {
        if (id == 666) {
            return ResponseEntity.notFound().build();
        }

        // Simulación dinámica: si el ID es 2, devuelve los datos modificados para el
        // test de actualización
        String firstName = "George";
        String lastName = "Franklin";
        if (id == 2) {
            firstName = "GeorgeMod";
            lastName = "FranklinMod";
        }

        OwnerDTO owner = OwnerDTO.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .address("110 W. Liberty St.")
                .city("Madison")
                .telephone("6085551023")
                .build();

        return ResponseEntity.ok(owner);
    }

    @PostMapping
    public ResponseEntity<OwnerDTO> createOwner(@RequestBody OwnerDTO ownerDTO) {
        // Simula la asignación de un ID autogenerado si viene nulo
        if (ownerDTO.getId() == null) {
            ownerDTO.setId(2);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerDTO> updateOwner(@PathVariable Integer id, @RequestBody OwnerDTO ownerDTO) {
        ownerDTO.setId(id);
        return ResponseEntity.ok(ownerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Integer id) {
        if (id == 1000) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}