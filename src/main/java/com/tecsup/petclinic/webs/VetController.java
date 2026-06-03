package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class VetController {

    private VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping("/vets")
    public ResponseEntity<List<VetDTO>> findAll() {
        return ResponseEntity.ok(vetService.findAll());
    }

    @GetMapping("/vets/{id}")
    public ResponseEntity<VetDTO> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(vetService.findById(id));
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/vets")
    public ResponseEntity<VetDTO> create(@RequestBody VetDTO vetDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vetService.create(vetDTO));
    }

    @PutMapping("/vets/{id}")
    public ResponseEntity<VetDTO> update(@RequestBody VetDTO vetDTO, @PathVariable Integer id) {
        try {
            VetDTO existing = vetService.findById(id);
            existing.setFirstName(vetDTO.getFirstName());
            existing.setLastName(vetDTO.getLastName());
            return ResponseEntity.ok(vetService.update(existing));
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/vets/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            vetService.delete(id);
            return ResponseEntity.ok("Delete ID: " + id);
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}