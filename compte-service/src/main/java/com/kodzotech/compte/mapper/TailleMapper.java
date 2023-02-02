package com.kodzotech.compte.mapper;

import com.kodzotech.compte.dto.TailleDto;
import com.kodzotech.compte.model.Taille;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TailleMapper {

    public Taille dtoToEntity(TailleDto tailleDto);

    @Mapping(target = "id", ignore = true)
    public Taille dtoToEntity(@MappingTarget Taille tailleOriginal, Taille tailleModifie);

    public TailleDto entityToDto(Taille taille);

}
