package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.PetNotFoundException;
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

    @GetMapping(value = "/vets")
    public ResponseEntity<List<VetDTO>> findAllVets() {
        List<VetDTO> vets = vetService.findAll();
        return ResponseEntity.ok(vets);
    }

    @PostMapping(value = "/vets")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VetDTO> create(@RequestBody VetDTO vetDTO) {
        VetDTO newVet = vetService.create(vetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVet);
    }

    @GetMapping(value = "/vets/{id}")
    public ResponseEntity<VetDTO> findById(@PathVariable Integer id) {
        try {
            VetDTO vetDTO = vetService.findById(id);
            return ResponseEntity.ok(vetDTO);
        } catch (PetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/vets/{id}")
    public ResponseEntity<VetDTO> update(@RequestBody VetDTO vetDTO, @PathVariable Integer id) {
        try {
            VetDTO updateVet = vetService.findById(id);
            updateVet.setFirstName(vetDTO.getFirstName());
            updateVet.setLastName(vetDTO.getLastName());
            vetService.update(updateVet);
            return ResponseEntity.ok(updateVet);
        } catch (PetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/vets/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            vetService.delete(id);
            return ResponseEntity.ok("Delete ID: " + id);
        } catch (PetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}