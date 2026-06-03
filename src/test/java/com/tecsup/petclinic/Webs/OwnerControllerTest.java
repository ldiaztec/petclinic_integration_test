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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Orden estricto para evitar conflictos entre borrar y buscar
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testFindAllOwners() throws Exception {
        this.mockMvc.perform(get("/api/owners"))
                .andDo(print())
                .andExpect(status().isOk())
                // Formato HAL de Spring Data REST para colecciones
                .andExpect(jsonPath("$._embedded.owners", notNullValue()));
    }

    @Test
    @Order(2)
    public void testFindOwnerById() throws Exception {
        // Consultamos un ID que sabemos que existe inicialmente (ID 2: Betty Davis)
        this.mockMvc.perform(get("/api/owners/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Betty")))
                .andExpect(jsonPath("$.lastName", is("Davis")));
    }

    @Test
    @Order(3)
    public void testCreateOwner() throws Exception {
        // Objeto JSON con los datos obligatorios para un Owner
        String jsonOwner = "{\"firstName\":\"Jose\",\"lastName\":\"Purizaga\",\"address\":\"Av. Tecsup 123\",\"city\":\"Lima\",\"telephone\":\"987654321\"}";

        this.mockMvc.perform(post("/api/owners")
                .content(jsonOwner)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                // Spring Data REST devuelve la cabecera Location con la URL del nuevo registro creado
                .andExpect(header().string("Location", notNullValue()));
    }

    @Test
    @Order(4)
    public void testDeleteOwner() throws Exception {
        // Eliminamos el ID 1 (George Franklin) al final para no afectar otros flujos
        this.mockMvc.perform(delete("/api/owners/1"))
                .andDo(print())
                // 204 No Content es el estándar correcto para Spring Data REST al borrar con éxito
                .andExpect(status().isNoContent());
    }
}