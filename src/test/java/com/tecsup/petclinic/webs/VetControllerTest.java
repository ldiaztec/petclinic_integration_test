package com.tecsup.petclinic.webs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Prueba de Búsqueda de todos los elementos
    @Test
    public void testFindAllVets() throws Exception {
        int NRO_RECORD = 6; 
        int ID_FIRST_RECORD = 1;

        // Simulamos la petición GET a /vets
        this.mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(NRO_RECORD)))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }

    @Test
    public void testCreateVet() throws Exception {
        String VET_FIRST_NAME = "Jonel";
        String VET_LAST_NAME = "Villanueva";

        // Creamos un JSON enviando los datos como si fuera un TO
        String newVetJson = "{\"firstName\":\"" + VET_FIRST_NAME + "\",\"lastName\":\"" + VET_LAST_NAME + "\"}";

        this.mockMvc.perform(post("/vets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newVetJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(VET_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(VET_LAST_NAME)));
    }

    @Test
    public void testDeleteVet() throws Exception {
        int ID_TO_DELETE = 1;

        this.mockMvc.perform(delete("/vets/" + ID_TO_DELETE))
                .andExpect(status().isOk());
    }
}