package com.kodzotech.documentcommercial.mapper;

import com.kodzotech.documentcommercial.dto.UniteDto;
import com.kodzotech.documentcommercial.model.Unite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UniteMapper {

    public Unite dtoToEntity(UniteDto uniteDto);

    @Mapping(target = "id", ignore = true)
    public Unite dtoToEntity(@MappingTarget Unite uniteOriginal, Unite uniteModifie);

    public UniteDto entityToDto(Unite unite);
}
