package com.tecsup.petclinic.webs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
@Transactional
public class SpecialtyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllSpecialties() throws Exception {
        
        this.mockMvc.perform(get("/specialties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray()) 
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    public void testCreateSpecialty() throws Exception {
        String SPECIALTY_NAME = "Dermatology";

        String newSpecialtyJson = "{\"name\":\"" + SPECIALTY_NAME + "\"}";

        this.mockMvc.perform(post("/specialties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newSpecialtyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(SPECIALTY_NAME)));
    }

    @Test
    public void testDeleteSpecialty() throws Exception {
        int ID_TO_DELETE = 1;

        this.mockMvc.perform(delete("/specialties/" + ID_TO_DELETE))
                .andExpect(status().isOk());
    }
}