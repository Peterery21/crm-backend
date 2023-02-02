package com.kodzotech.compte.mapper;

import com.kodzotech.compte.dto.CategorieCompteDto;
import com.kodzotech.compte.model.CategorieCompte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategorieCompteMapper {

    public CategorieCompte dtoToEntity(CategorieCompteDto categorieCompteDto);

    @Mapping(target = "id", ignore = true)
    public CategorieCompte entityToDto(@MappingTarget CategorieCompte categorieCompteOriginal,
                                       CategorieCompte categorieCompteModifie);

    public CategorieCompteDto entityToDto(CategorieCompte categorieCompte);
}
