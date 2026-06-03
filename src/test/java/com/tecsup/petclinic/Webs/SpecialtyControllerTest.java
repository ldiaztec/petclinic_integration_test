package com.tecsup.petclinic.webs;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SpecialtyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllSpecialties() throws Exception {
        this.mockMvc.perform(get("/api/v1/specialties"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testFindSpecialtyById() throws Exception {
        this.mockMvc.perform(get("/api/v1/specialties/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("radiology")));
    }

    @Test
    public void testCreateSpecialty() throws Exception {
        String jsonSpecialty = "{\"name\":\"Cardiology\"}";

        this.mockMvc.perform(post("/api/v1/specialties")
                .content(jsonSpecialty)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Cardiology")));
    }
}
