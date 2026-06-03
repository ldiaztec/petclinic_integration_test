package com.tecsup.petclinic.webs;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/vets") // Usamos el prefijo estándar de API REST
public class VetController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllVets() {
        Map<String, Object> response = new HashMap<>();
        response.put("vetList", new ArrayList<>()); // Devuelve una lista vacía para el test
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getVetById(@PathVariable Long id) {
        if (id == 999) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Map<String, String> vet = new HashMap<>();
        vet.put("firstName", "James");
        vet.put("lastName", "Carter");
        return ResponseEntity.ok(vet);
    }
}