package com.kodzotech.documentcommercial.mapper;

import com.kodzotech.documentcommercial.dto.MarqueDto;
import com.kodzotech.documentcommercial.model.Marque;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MarqueMapper {

    public Marque dtoToEntity(MarqueDto marqueDto);

    @Mapping(target = "id", ignore = true)
    public Marque dtoToEntity(@MappingTarget Marque marqueOriginal, Marque marqueModifie);

    public MarqueDto entityToDto(Marque marque);
}
