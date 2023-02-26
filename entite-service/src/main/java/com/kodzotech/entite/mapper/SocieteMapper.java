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
public interface SocieteMapper {

    public Societe dtoToEntity(SocieteDto societeDto);

    public SocieteDto entityToDto(Societe societe);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public Societe dtoToEntity(@MappingTarget Societe societeOriginal,
                                        Societe societeModifier);
}
