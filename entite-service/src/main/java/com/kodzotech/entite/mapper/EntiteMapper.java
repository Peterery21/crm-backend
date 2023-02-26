package com.kodzotech.entite.mapper;


import com.kodzotech.entite.dto.AdresseDto;
import com.kodzotech.entite.dto.EntiteDto;
import com.kodzotech.entite.dto.EntiteResponse;
import com.kodzotech.entite.dto.SocieteDto;
import com.kodzotech.entite.model.Entite;
import com.kodzotech.entite.client.AdresseClient;
import com.kodzotech.entite.repository.EntiteRepository;
import com.kodzotech.entite.repository.SocieteRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EntiteMapper {

    @Mapping(target = "parentId", source = "parent.id")
    public EntiteDto entityToDto(Entite entite);

    public Entite dtoToEntity(EntiteDto entiteDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent.id", source = "parent.id")
    public Entite dtoToEntity(@MappingTarget Entite entiteOriginal, Entite entiteModifie);

    public EntiteResponse entityToResponse(Entite entite);

}
