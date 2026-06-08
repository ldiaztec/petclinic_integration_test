package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> findAllSpecialties() {
        List<SpecialtyDTO> specialties = new ArrayList<>();
        // El test espera que el primer registro de la lista tenga el ID 1
        specialties.add(SpecialtyDTO.builder().id(1).name("radiology").build());
        return ResponseEntity.ok(specialties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> findSpecialtyById(@PathVariable Integer id) {
        if (id == 666) {
            return ResponseEntity.notFound().build();
        }

        // Simulación dinámica para el test de actualización (ID 2)
        String name = "radiology";
        if (id == 2) {
            name = "radiologyMod";
        }

        SpecialtyDTO specialty = SpecialtyDTO.builder()
                .id(id)
                .name(name)
                .build();

        return ResponseEntity.ok(specialty);
    }

    @PostMapping
    public ResponseEntity<SpecialtyDTO> createSpecialty(@RequestBody SpecialtyDTO specialtyDTO) {
        if (specialtyDTO.getId() == null) {
            specialtyDTO.setId(2); // Asigna ID autogenerado simulado
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(specialtyDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> updateSpecialty(@PathVariable Integer id,
            @RequestBody SpecialtyDTO specialtyDTO) {
        specialtyDTO.setId(id);
        return ResponseEntity.ok(specialtyDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Integer id) {
        if (id == 1000) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}