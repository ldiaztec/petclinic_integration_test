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

    /**
     * Prueba la obtención de todos los dueños.
     * Verifica que el primer registro coincida con el ID esperado (1).
     */
    @Test
    public void testFindAllOwners() throws Exception {
        int ID_FIRST_RECORD = 1;

        this.mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }

    /**
     * Prueba la obtención exitosa de un dueño específico por ID.
     */
    @Test
    public void testFindOwnerOK() throws Exception {
        String OWNER_FIRSTNAME = "George";
        String OWNER_LASTNAME = "Franklin";

        this.mockMvc.perform(get("/owners/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is(OWNER_FIRSTNAME)))
                .andExpect(jsonPath("$.lastName", is(OWNER_LASTNAME)));
    }

    /**
     * Prueba el caso de error cuando el dueño buscado no existe (ID 666).
     */
    @Test
    public void testFindOwnerKO() throws Exception {
        mockMvc.perform(get("/owners/666"))
                .andExpect(status().isNotFound());
    }

    /**
     * Prueba la creación de un nuevo dueño en el sistema.
     */
    @Test
    public void testCreateOwner() throws Exception {
        String OWNER_FIRSTNAME = "Betty";
        String OWNER_LASTNAME = "Davis";

        OwnerDTO newOwnerTO = OwnerDTO.builder()
                .firstName(OWNER_FIRSTNAME)
                .lastName(OWNER_LASTNAME)
                .address("638 Cardinal Ave.")
                .city("Sun Prairie")
                .telephone("6085551749")
                .build();

        this.mockMvc.perform(post("/owners")
                .content(om.writeValueAsString(newOwnerTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(OWNER_FIRSTNAME)))
                .andExpect(jsonPath("$.lastName", is(OWNER_LASTNAME)));
    }

    /**
     * Prueba la eliminación física de un dueño.
     * Registra un objeto temporal, extrae su ID con JsonPath y procede a borrarlo.
     */
    @Test
    public void testDeleteOwner() throws Exception {
        OwnerDTO newOwnerTO = OwnerDTO.builder()
                .firstName("Eduardo")
                .lastName("Rodriquez")
                .address("2693 Commerce St.")
                .city("McFarland")
                .telephone("6085558763")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                .content(om.writeValueAsString(newOwnerTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/owners/" + id))
                .andExpect(status().isOk());
    }

    /**
     * Prueba el error de eliminación cuando el ID no existe (ID 1000).
     */
    @Test
    public void testDeleteOwnerKO() throws Exception {
        mockMvc.perform(delete("/owners/" + "1000"))
                .andExpect(status().isNotFound());
    }

    /**
     * Prueba completa del ciclo de actualización (PUT).
     * Modifica el registro, lo consulta via GET para asegurar la persistencia y
     * limpia la BD.
     */
    @Test
    public void testUpdateOwner() throws Exception {
        String OWNER_FIRSTNAME = "George";
        String OWNER_LASTNAME = "Franklin";

        String UP_OWNER_FIRSTNAME = "GeorgeMod";
        String UP_OWNER_LASTNAME = "FranklinMod";

        OwnerDTO newOwnerTO = OwnerDTO.builder()
                .firstName(OWNER_FIRSTNAME)
                .lastName(OWNER_LASTNAME)
                .address("110 W. Liberty St.")
                .city("Madison")
                .telephone("6085551023")
                .build();

        // 1. CREAR
        ResultActions mvcActions = mockMvc.perform(post("/owners")
                .content(om.writeValueAsString(newOwnerTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        // 2. ACTUALIZAR
        OwnerDTO upOwnerTO = OwnerDTO.builder()
                .id(id)
                .firstName(UP_OWNER_FIRSTNAME)
                .lastName(UP_OWNER_LASTNAME)
                .build();

        mockMvc.perform(put("/owners/" + id)
                .content(om.writeValueAsString(upOwnerTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 3. ENCONTRAR Y VALIDAR CAMBIOS
        mockMvc.perform(get("/owners/" + id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.firstName", is(UP_OWNER_FIRSTNAME)))
                .andExpect(jsonPath("$.lastName", is(UP_OWNER_LASTNAME)));

        // 4. LIMPIAR (BORRAR)
        mockMvc.perform(delete("/owners/" + id))
                .andExpect(status().isOk());
    }
}