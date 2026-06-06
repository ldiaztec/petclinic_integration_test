package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.SpecialtyDTO;
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
public class SpecialtyControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllSpecialties() throws Exception {
        this.mockMvc.perform(get("/specialties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    public void testFindSpecialtyOK() throws Exception {
        this.mockMvc.perform(get("/specialties/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("radiology")));
    }

    @Test
    public void testFindSpecialtyKO() throws Exception {
        mockMvc.perform(get("/specialties/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateSpecialty() throws Exception {
        SpecialtyDTO newSpecialty = SpecialtyDTO.builder()
                .name("cardiology")
                .build();

        this.mockMvc.perform(post("/specialties")
                .content(om.writeValueAsString(newSpecialty))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("cardiology")));
    }

    @Test
    public void testDeleteSpecialty() throws Exception {
        SpecialtyDTO newSpecialty = SpecialtyDTO.builder()
                .name("toDelete")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/specialties")
                .content(om.writeValueAsString(newSpecialty))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/specialties/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSpecialtyKO() throws Exception {
        mockMvc.perform(delete("/specialties/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateSpecialty() throws Exception {
        SpecialtyDTO newSpecialty = SpecialtyDTO.builder()
                .name("neurology")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/specialties")
                .content(om.writeValueAsString(newSpecialty))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        SpecialtyDTO updated = SpecialtyDTO.builder()
                .name("neuroscience")
                .build();

        mockMvc.perform(put("/specialties/" + id)
                .content(om.writeValueAsString(updated))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/specialties/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("neuroscience")));

        mockMvc.perform(delete("/specialties/" + id))
                .andExpect(status().isOk());
    }
}