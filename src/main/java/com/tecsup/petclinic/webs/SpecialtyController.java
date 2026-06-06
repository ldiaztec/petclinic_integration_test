package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.PetNotFoundException;
import com.tecsup.petclinic.services.SpecialtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class SpecialtyController {

    private SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping(value = "/specialties")
    public ResponseEntity<List<SpecialtyDTO>> findAllSpecialties() {
        List<SpecialtyDTO> specialties = specialtyService.findAll();
        return ResponseEntity.ok(specialties);
    }

    @PostMapping(value = "/specialties")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SpecialtyDTO> create(@RequestBody SpecialtyDTO specialtyDTO) {
        SpecialtyDTO newSpecialty = specialtyService.create(specialtyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSpecialty);
    }

    @GetMapping(value = "/specialties/{id}")
    public ResponseEntity<SpecialtyDTO> findById(@PathVariable Integer id) {
        try {
            SpecialtyDTO specialtyDTO = specialtyService.findById(id);
            return ResponseEntity.ok(specialtyDTO);
        } catch (PetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/specialties/{id}")
    public ResponseEntity<SpecialtyDTO> update(@RequestBody SpecialtyDTO specialtyDTO, @PathVariable Integer id) {
        try {
            SpecialtyDTO updateSpecialty = specialtyService.findById(id);
            updateSpecialty.setName(specialtyDTO.getName());
            specialtyService.update(updateSpecialty);
            return ResponseEntity.ok(updateSpecialty);
        } catch (PetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/specialties/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            specialtyService.delete(id);
            return ResponseEntity.ok("Delete ID: " + id);
        } catch (PetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}