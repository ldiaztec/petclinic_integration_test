package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.VetDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VetControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllVets() throws Exception {
        final int ID_FIRST_RECORD = 1;
        this.mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }

    @Test
    public void testFindVetOK() throws Exception {
        this.mockMvc.perform(get("/vets/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("James")))
                .andExpect(jsonPath("$.lastName", is("Carter")));
    }

    @Test
    public void testFindVetKO() throws Exception {
        mockMvc.perform(get("/vets/666"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateVet() throws Exception {
        VetDTO newVet = VetDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        this.mockMvc.perform(post("/vets")
                .content(om.writeValueAsString(newVet))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    public void testDeleteVet() throws Exception {
        VetDTO newVet = VetDTO.builder()
                .firstName("ToDelete")
                .lastName("Vet")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/vets")
                .content(om.writeValueAsString(newVet))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/vets/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteVetKO() throws Exception {
        mockMvc.perform(delete("/vets/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateVet() throws Exception {
        VetDTO newVet = VetDTO.builder()
                .firstName("Original")
                .lastName("Name")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/vets")
                .content(om.writeValueAsString(newVet))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        VetDTO updated = VetDTO.builder()
                .id(id)
                .firstName("Updated")
                .lastName("Vet")
                .build();

        mockMvc.perform(put("/vets/" + id)
                .content(om.writeValueAsString(updated))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/vets/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Updated")))
                .andExpect(jsonPath("$.lastName", is("Vet")));

        mockMvc.perform(delete("/vets/" + id))
                .andExpect(status().isOk());
    }
}