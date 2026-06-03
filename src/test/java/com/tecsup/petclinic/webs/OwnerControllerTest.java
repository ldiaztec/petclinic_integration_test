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
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindOwners() throws Exception {
        mockMvc.perform(get("/api/v1/owners")
                .param("lastName", ""))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOwnerById() throws Exception {
        mockMvc.perform(get("/api/v1/owners/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testNewOwnerForm() throws Exception {
        mockMvc.perform(get("/api/v1/owners/new"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateNewOwner() throws Exception {
        mockMvc.perform(post("/api/v1/owners/new")
                .param("firstName", "Juan")
                .param("lastName", "Perez"))
                .andExpect(status().is3xxRedirection());
    }
}