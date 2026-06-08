package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vets")
public class VetController {

    @Autowired
    private VetService vetService;

    @GetMapping
    public ResponseEntity<List<Vet>> findAllVets() {
        List<Vet> vets = vetService.findAll();
        return ResponseEntity.ok(vets);
    }

    @PostMapping
    public ResponseEntity<Vet> createVet(@RequestBody Vet vet) {
        Vet newVet = vetService.create(vet);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVet(@PathVariable Integer id) {
        try {
            vetService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}