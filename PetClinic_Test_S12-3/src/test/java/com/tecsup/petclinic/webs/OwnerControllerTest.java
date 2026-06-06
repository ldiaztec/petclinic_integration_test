package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.OwnerDTO;
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
public class OwnerControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllOwners() throws Exception {
        this.mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void testFindOwnerOK() throws Exception {
        this.mockMvc.perform(get("/owners/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("George")))
                .andExpect(jsonPath("$.lastName", is("Franklin")))
                .andExpect(jsonPath("$.address", is("110 W. Liberty St.")))
                .andExpect(jsonPath("$.city", is("Madison")))
                .andExpect(jsonPath("$.telephone", is("6085551023")));
    }

    @Test
    public void testFindOwnerKO() throws Exception {
        mockMvc.perform(get("/owners/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOwner() throws Exception {
        OwnerDTO newOwner = OwnerDTO.builder()
                .firstName("John")
                .lastName("Smith")
                .address("123 Main St.")
                .city("Springfield")
                .telephone("6085559999")
                .build();

        this.mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwner))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Smith")))
                .andExpect(jsonPath("$.city", is("Springfield")));
    }

    @Test
    public void testDeleteOwner() throws Exception {
        OwnerDTO newOwner = OwnerDTO.builder()
                .firstName("ToDelete")
                .lastName("Owner")
                .address("999 Delete St.")
                .city("Nowhere")
                .telephone("0000000000")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwner))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/owners/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteOwnerKO() throws Exception {
        mockMvc.perform(delete("/owners/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateOwner() throws Exception {
        OwnerDTO newOwner = OwnerDTO.builder()
                .firstName("Original")
                .lastName("Owner")
                .address("100 Old St.")
                .city("OldCity")
                .telephone("1111111111")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwner))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        OwnerDTO updated = OwnerDTO.builder()
                .firstName("Updated")
                .lastName("Owner")
                .address("200 New St.")
                .city("NewCity")
                .telephone("2222222222")
                .build();

        mockMvc.perform(put("/owners/" + id)
                        .content(om.writeValueAsString(updated))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/owners/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Updated")))
                .andExpect(jsonPath("$.city", is("NewCity")));

        mockMvc.perform(delete("/owners/" + id))
                .andExpect(status().isOk());
    }
}