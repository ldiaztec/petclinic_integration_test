package com.tecsup.petclinic.webs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
public class SpecialtyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllSpecialties() throws Exception {
        mockMvc.perform(get("/api/v1/specialties")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSpecialtyById() throws Exception {
        mockMvc.perform(get("/api/v1/specialties/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateSpecialty() throws Exception {
        String newSpecialty = "{\"name\": \"Cardiologia\"}";
        mockMvc.perform(post("/api/v1/specialties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newSpecialty))
                .andExpect(status().isCreated());
    }
}