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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
@Transactional
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllOwners() throws Exception {
        int NRO_RECORD = 10; 
        int ID_FIRST_RECORD = 1;

        this.mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(NRO_RECORD)))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }

    @Test
    public void testCreateOwner() throws Exception {
        String OWNER_FIRST_NAME = "Eduardo";
        String OWNER_LAST_NAME = "Diaz";
        String OWNER_CITY = "Lima";

        String newOwnerJson = "{\"firstName\":\"" + OWNER_FIRST_NAME + 
                              "\",\"lastName\":\"" + OWNER_LAST_NAME + 
                              "\",\"city\":\"" + OWNER_CITY + "\"}";

        this.mockMvc.perform(post("/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newOwnerJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(OWNER_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(OWNER_LAST_NAME)));
    }

    @Test
    public void testDeleteOwner() throws Exception {
        int ID_TO_DELETE = 1;

        this.mockMvc.perform(delete("/owners/" + ID_TO_DELETE))
                .andExpect(status().isOk());
    }
}