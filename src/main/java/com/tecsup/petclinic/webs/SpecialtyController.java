package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.services.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<List<Specialty>> findAllSpecialties() {
        List<Specialty> specialties = specialtyService.findAll();
        return ResponseEntity.ok(specialties);
    }

    @PostMapping
    public ResponseEntity<Specialty> createSpecialty(@RequestBody Specialty specialty) {
        Specialty newSpecialty = specialtyService.create(specialty);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSpecialty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Integer id) {
        try {
            specialtyService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}