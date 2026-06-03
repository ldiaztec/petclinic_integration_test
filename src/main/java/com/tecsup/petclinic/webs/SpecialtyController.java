package com.tecsup.petclinic.webs;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/specialties")
public class SpecialtyController {

    @GetMapping
    public ResponseEntity<Void> getAllSpecialties() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> getSpecialtyById(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createSpecialty(@RequestBody Map<String, String> body) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}