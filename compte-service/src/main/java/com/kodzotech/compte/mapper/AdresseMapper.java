package com.kodzotech.compte.mapper;

import com.kodzotech.compte.dto.AdresseDto;
import com.kodzotech.compte.model.Adresse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AdresseMapper {

    public AdresseDto entityToDto(Adresse adresse);

    @Mapping(target = "id", ignore = true)
    public Adresse fillEntity(@MappingTarget Adresse adresseOriginal, Adresse adresse);

    public Adresse dtoToEntity(AdresseDto adresseDto);
}
