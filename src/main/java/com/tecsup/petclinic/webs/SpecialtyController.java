package com.tecsup.petclinic.webs;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.services.SpecialtyService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping("/specialties")
    public ResponseEntity<List<SpecialtyDTO>> findAll() {
        return ResponseEntity.ok(specialtyService.findAll());
    }

    @GetMapping("/specialties/{id}")
    public ResponseEntity<SpecialtyDTO> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(specialtyService.findById(id));
        } catch (SpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/specialties")
    public ResponseEntity<SpecialtyDTO> create(@RequestBody SpecialtyDTO specialtyDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(specialtyService.create(specialtyDTO));
    }

    @PutMapping("/specialties/{id}")
    public ResponseEntity<SpecialtyDTO> update(@RequestBody SpecialtyDTO specialtyDTO, @PathVariable Integer id) {
        try {
            SpecialtyDTO existing = specialtyService.findById(id);
            existing.setName(specialtyDTO.getName());
            return ResponseEntity.ok(specialtyService.update(existing));
        } catch (SpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/specialties/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            specialtyService.delete(id);
            return ResponseEntity.ok("Delete ID: " + id);
        } catch (SpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}