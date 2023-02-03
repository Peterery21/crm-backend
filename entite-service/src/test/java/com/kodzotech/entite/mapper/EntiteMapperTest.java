package com.kodzotech.entite.mapper;

import com.kodzotech.entite.dto.AdresseDto;
import com.kodzotech.entite.dto.EntiteDto;
import com.kodzotech.entite.dto.EntiteResponse;
import com.kodzotech.entite.model.Entite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EntiteMapperTest {

    EntiteMapper entiteMapper = Mappers.getMapper(EntiteMapper.class);
    @Test
    void givenEntite_whenMapToDto_thenReturnEntiteDto() {
        EntiteDto actualEntiteDto = entiteMapper.entityToDto(buildEntitePayload());
        assertEquals(buildEntiteDtoPayload(), actualEntiteDto);
    }

    @Test
    void givenEntiteDto_whenMapToEntite_thenReturnEntite() {
        Entite actualEntite = entiteMapper.dtoToEntity(buildEntiteDtoPayload());
        assertEquals(buildEntitePayload(), actualEntite);
    }

    @Test
    void given2Entite_whenEntiteToDto_thenReturnMergedEntite() {
        Entite entite = entiteMapper.dtoToEntity(buildEntitePayload(), buildEntitePayload());
        assertEquals(buildEntitePayload(), entite);
    }

    @Test
    void givenEntite_whenMapToResponse_thenReturnEntiteResponse() {
        EntiteResponse actualEntiteResponse = entiteMapper.entityToResponse(buildEntitePayload());
        assertEquals(buildEntiteResponsePayload(), actualEntiteResponse);
    }

    Entite buildEntitePayload(){
        return Entite.builder()
                .id(5L)
                .nom("TEST")
                .deviseId(1L)
                .niveau(1)
                .societeId(1L)
                .build();
    }

    EntiteDto buildEntiteDtoPayload(){
        return EntiteDto.builder()
                .id(5L)
                .nom("TEST")
                .deviseId(1L)
                .niveau(1)
                .societeId(1L)
                .build();
    }

    EntiteResponse buildEntiteResponsePayload(){
        return EntiteResponse.builder()
                .id(5L)
                .nom("TEST")
                .niveau(1)
                .build();
    }
}