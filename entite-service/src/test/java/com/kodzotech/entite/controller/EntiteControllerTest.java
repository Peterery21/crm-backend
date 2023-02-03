package com.kodzotech.entite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodzotech.entite.dto.EntiteDto;
import com.kodzotech.entite.service.EntiteChartService;
import com.kodzotech.entite.service.EntiteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(EntiteController.class)
class EntiteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EntiteService entiteService;
    @MockBean
    private  EntiteChartService entiteChartService;
    @Value("classpath:payload/valid-entitedto.json")
    private Resource entiteDtoPayload;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Test save method with valid payload")
    void givenValidEntiteDto_WhenSave_ThenReturnStatutCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/entites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new String(entiteDtoPayload.getInputStream().readAllBytes())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    @DisplayName("Test save with invalid payload then return BadRequest")
    void givenInvalidEntiteDto_WhenSave_ThenReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/entites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test getEntite")
    void givenEntiteId_whenGet_ThenReturnEntiteDto() throws Exception {

        EntiteDto entiteDto = EntiteDto.builder()
                .nom("siege").build();
        Mockito.when(entiteService.getEntite(ArgumentMatchers.anyLong()))
                .thenReturn(entiteDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/entites/1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        EntiteDto actualEntite = objectMapper
                .readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                        EntiteDto.class);

        assertEquals(entiteDto, actualEntite);

    }

    @Test
    @DisplayName("Test getAllEntite bySociete")
    void givenSocieteId_whenGetAllEntite_ThenReturnListOfEntiteDto() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/entites/bySociete/1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        Mockito.verify(entiteChartService).getAllEntitesBySociete(1L);

    }

    @Test
    @DisplayName("Test delete entite method")
    void givenEntiteId_whenDelete_ThenDeleteEntiteAndStatusOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/entites/1")
                );
        Mockito.verify(entiteService).delete(1L);

    }

    @Test
    @DisplayName("Test getEntiteChart bySociete")
    void givenSocieteId_whenGetEntiteChart_ThenReturnEntiteChart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/entites/chart/1")
                );
        Mockito.verify(entiteChartService).getEntiteChart(1L);
    }

    @Test
    @DisplayName("Test update valid entite")
    void givenValidEntiteDto_whenUpdate_ThenReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/entites/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new String(entiteDtoPayload.getInputStream().readAllBytes()))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(entiteService).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Test update invalid entite")
    void givenInvalidEntiteDto_whenUpdate_ThenReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/entites/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}