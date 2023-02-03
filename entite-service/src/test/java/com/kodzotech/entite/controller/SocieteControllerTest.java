package com.kodzotech.entite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodzotech.entite.dto.SocieteDto;
import com.kodzotech.entite.model.Societe;
import com.kodzotech.entite.service.SocieteService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SocieteController.class)
class SocieteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SocieteService societeService;
    @Value("classpath:payload/valid-societedto.json")
    private Resource societeDtoPayload;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenValidSocieteDto_whenSave_thenReturnStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/societes")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(societeDtoPayload.getInputStream().readAllBytes())
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(societeService).save(ArgumentMatchers.any());
    }

    @Test
    void givenValidSocieteDto_whenUpdate_thenReturn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/societes/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(societeDtoPayload.getInputStream().readAllBytes())
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(societeService).save(ArgumentMatchers.any(SocieteDto.class));
    }

    @Test
    void givenSocieteId_whenGetSociete_thenReturnSocieteDtoAndStatutOk() throws Exception {

        SocieteDto societeDto = SocieteDto.builder()
                .id(1L)
                .raisonSociale("Google")
                .logo("logo.jpg")
                .build();
        Mockito.when(societeService.getSociete(ArgumentMatchers.anyLong()))
                .thenReturn(societeDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/societes/1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        SocieteDto actualSociete = objectMapper
                .readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                        SocieteDto.class);
        assertEquals(societeDto, actualSociete);
        assertNotNull(societeDto.getLogoUrl());
    }
}