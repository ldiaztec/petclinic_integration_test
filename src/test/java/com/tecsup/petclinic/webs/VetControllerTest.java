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

    /**
     * Prueba la obtención de todos los veterinarios.
     * Verifica que el primer registro tenga el ID esperado (1).
     */
    @Test
    public void testFindAllVets() throws Exception {
        final int ID_FIRST_RECORD = 1;

        this.mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }

    /**
     * Prueba la obtención exitosa de un veterinario específico por ID.
     */
    @Test
    public void testFindVetOK() throws Exception {
        String VET_FIRSTNAME = "James";
        String VET_LASTNAME = "Carter";

        this.mockMvc.perform(get("/vets/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is(VET_FIRSTNAME)))
                .andExpect(jsonPath("$.lastName", is(VET_LASTNAME)));
    }

    /**
     * Prueba el caso de error cuando el veterinario buscado no existe (ID 666).
     */
    @Test
    public void testFindVetKO() throws Exception {
        mockMvc.perform(get("/vets/666"))
                .andExpect(status().isNotFound());
    }

    /**
     * Prueba la creación de un nuevo veterinario en el sistema.
     */
    @Test
    public void testCreateVet() throws Exception {
        String VET_FIRSTNAME = "Helen";
        String VET_LASTNAME = "Leary";

        VetDTO newVetTO = VetDTO.builder()
                .firstName(VET_FIRSTNAME)
                .lastName(VET_LASTNAME)
                .build();

        this.mockMvc.perform(post("/vets")
                .content(om.writeValueAsString(newVetTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(VET_FIRSTNAME)))
                .andExpect(jsonPath("$.lastName", is(VET_LASTNAME)));
    }

    /**
     * Prueba la eliminación física de un veterinario.
     * Sigue la lógica de crear un registro temporal, extraer su ID y luego
     * borrarlo.
     */
    @Test
    public void testDeleteVet() throws Exception {
        VetDTO newVetTO = VetDTO.builder()
                .firstName("Linda")
                .lastName("Douglas")
                .build();

        // 1. CREAR REGISTRO TEMPORAL
        ResultActions mvcActions = mockMvc.perform(post("/vets")
                .content(om.writeValueAsString(newVetTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        // 2. ELIMINAR EL REGISTRO GENERADO
        mockMvc.perform(delete("/vets/" + id))
                .andExpect(status().isOk());
    }

    /**
     * Prueba el error de eliminación cuando el ID no existe en el sistema (ID
     * 1000).
     */
    @Test
    public void testDeleteVetKO() throws Exception {
        mockMvc.perform(delete("/vets/" + "1000"))
                .andExpect(status().isNotFound());
    }

    /**
     * Prueba completa del ciclo de actualización (PUT).
     * Crea un registro, lo modifica, valida los cambios con un GET y finalmente lo
     * limpia (DELETE).
     */
    @Test
    public void testUpdateVet() throws Exception {
        String VET_FIRSTNAME = "Rafael";
        String VET_LASTNAME = "Ortega";

        String UP_VET_FIRSTNAME = "RafaelMod";
        String UP_VET_LASTNAME = "OrtegaMod";

        VetDTO newVetTO = VetDTO.builder()
                .firstName(VET_FIRSTNAME)
                .lastName(VET_LASTNAME)
                .build();

        // 1. CREAR
        ResultActions mvcActions = mockMvc.perform(post("/vets")
                .content(om.writeValueAsString(newVetTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        // 2. ACTUALIZAR
        VetDTO upVetTO = VetDTO.builder()
                .id(id)
                .firstName(UP_VET_FIRSTNAME)
                .lastName(UP_VET_LASTNAME)
                .build();

        mockMvc.perform(put("/vets/" + id)
                .content(om.writeValueAsString(upVetTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 3. ENCONTRAR Y VALIDAR CAMBIOS
        mockMvc.perform(get("/vets/" + id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.firstName", is(UP_VET_FIRSTNAME)))
                .andExpect(jsonPath("$.lastName", is(UP_VET_LASTNAME)));

        // 4. LIMPIAR (BORRAR)
        mockMvc.perform(delete("/vets/" + id))
                .andExpect(status().isOk());
    }
}