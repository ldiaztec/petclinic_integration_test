package com.tecsup.petclinic.webs;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
public class VetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testFindAllVets() throws Exception {
        this.mockMvc.perform(get("/api/vets"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.vets", notNullValue()));
    }

    @Test
    @Order(2)
    public void testFindVetById() throws Exception {
        this.mockMvc.perform(get("/api/vets/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Helen")))
                .andExpect(jsonPath("$.lastName", is("Leary")));
    }

    @Test
    @Order(3)
    public void testCreateVet() throws Exception {
        String jsonVet = "{\"firstName\":\"Jose\",\"lastName\":\"Purizaga\"}";

        this.mockMvc.perform(post("/api/vets")
                .content(jsonVet)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", notNullValue()));
    }

    @Test
    @Order(4)
    public void testDeleteVet() throws Exception {
        this.mockMvc.perform(delete("/api/vets/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}