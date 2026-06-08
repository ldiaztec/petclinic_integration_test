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
        int ID_FIRST_RECORD = 1;

        this.mockMvc.perform(get("/specialties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }

    @Test
    public void testFindSpecialtyOK() throws Exception {
        String SPECIALTY_NAME = "radiology";

        this.mockMvc.perform(get("/specialties/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(SPECIALTY_NAME)));
    }

    @Test
    public void testFindSpecialtyKO() throws Exception {
        mockMvc.perform(get("/specialties/666"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateSpecialty() throws Exception {
        String SPECIALTY_NAME = "surgery";

        SpecialtyDTO newSpecialtyTO = SpecialtyDTO.builder()
                .name(SPECIALTY_NAME)
                .build();

        this.mockMvc.perform(post("/specialties")
                .content(om.writeValueAsString(newSpecialtyTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(SPECIALTY_NAME)));
    }

    @Test
    public void testDeleteSpecialty() throws Exception {
        SpecialtyDTO newSpecialtyTO = SpecialtyDTO.builder()
                .name("dentistry")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/specialties")
                .content(om.writeValueAsString(newSpecialtyTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/specialties/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSpecialtyKO() throws Exception {
        mockMvc.perform(delete("/specialties/" + "1000"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateSpecialty() throws Exception {
        String SPECIALTY_NAME = "radiology";
        String UP_SPECIALTY_NAME = "radiologyMod";

        SpecialtyDTO newSpecialtyTO = SpecialtyDTO.builder()
                .name(SPECIALTY_NAME)
                .build();

        // 1. CREAR
        ResultActions mvcActions = mockMvc.perform(post("/specialties")
                .content(om.writeValueAsString(newSpecialtyTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        // 2. ACTUALIZAR
        SpecialtyDTO upSpecialtyTO = SpecialtyDTO.builder()
                .id(id)
                .name(UP_SPECIALTY_NAME)
                .build();

        mockMvc.perform(put("/specialties/" + id)
                .content(om.writeValueAsString(upSpecialtyTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 3. ENCONTRAR Y VALIDAR
        mockMvc.perform(get("/specialties/" + id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(UP_SPECIALTY_NAME)));

        // 4. LIMPIAR
        mockMvc.perform(delete("/specialties/" + id))
                .andExpect(status().isOk());
    }
}