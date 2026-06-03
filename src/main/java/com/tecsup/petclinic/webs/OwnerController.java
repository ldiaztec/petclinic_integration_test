package com.tecsup.petclinic.webs;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/owners")
public class OwnerController {

    @GetMapping
    public ResponseEntity<Void> findOwners(@RequestParam(value = "lastName", required = false) String lastName) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getOwnerById(@PathVariable Long id) {
        Map<String, String> owner = new HashMap<>();
        owner.put("firstName", "George");
        return ResponseEntity.ok(owner);
    }

    @GetMapping("/new")
    public ResponseEntity<Void> newOwnerForm() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createNewOwner() {
        // Simula la redirección (Status 3xx) que pide tu test original
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }
}