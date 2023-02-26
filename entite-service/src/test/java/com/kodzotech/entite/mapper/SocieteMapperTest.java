package com.kodzotech.entite.mapper;

import com.kodzotech.entite.dto.SocieteDto;
import com.kodzotech.entite.model.Societe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SocieteMapperTest {

    SocieteMapper societeMapper = Mappers.getMapper(SocieteMapper.class);

    @Test
    void dtoToEntity() {
        Societe societe = societeMapper.dtoToEntity(buildSocieteDtoPayload());
        assertEquals(societe, buildSocietePayload());
    }

    @Test
    void entityToDto() {
        SocieteDto societeDto = societeMapper.entityToDto(buildSocietePayload());
        assertEquals(societeDto, buildSocieteDtoPayload());
    }

    @Test
    void testDtoToEntity() {
        Societe societe = societeMapper.dtoToEntity(buildSocietePayload(), buildSocietePayload());
        assertEquals(societe, buildSocietePayload());
    }

    SocieteDto buildSocieteDtoPayload(){
        return SocieteDto.builder()
                .id(1L)
                .raisonSociale("google")
                .logo("logo.jp")
                .build();
    }

    private Societe buildSocietePayload() {
        return Societe.builder()
                .id(1L)
                .raisonSociale("google")
                .logo("logo.jp")
                .build();
    }
}