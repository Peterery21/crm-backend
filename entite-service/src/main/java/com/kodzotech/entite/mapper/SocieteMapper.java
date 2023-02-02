package com.kodzotech.entite.mapper;

import com.kodzotech.entite.client.AdresseClient;
import com.kodzotech.entite.dto.AdresseDto;
import com.kodzotech.entite.dto.SocieteDto;
import com.kodzotech.entite.model.Societe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SocieteMapper {

    @Autowired
    private AdresseClient adresseClient;

    public abstract Societe dtoToEntity(SocieteDto societeDto);

    @Mapping(target = "adresse", expression = "java(getAdresseById(societe.getAdresseId()))")
    public abstract SocieteDto entityToDto(Societe societe);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Societe dtoToEntity(@MappingTarget Societe societeOriginal, Societe societeModifier);

    AdresseDto getAdresseById(Long adresseId) {
        List<AdresseDto> adresseDtoList = adresseClient.getAdressesById(Arrays.asList(adresseId));
        if (!adresseDtoList.isEmpty()) {
            return adresseDtoList.get(0);
        } else {
            return null;
        }
    }
}
