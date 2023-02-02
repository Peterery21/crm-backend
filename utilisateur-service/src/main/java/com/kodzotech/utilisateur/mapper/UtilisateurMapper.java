package com.kodzotech.utilisateur.mapper;

import com.kodzotech.utilisateur.client.EntiteClient;
import com.kodzotech.utilisateur.dto.EntiteDto;
import com.kodzotech.utilisateur.dto.UtilisateurDto;
import com.kodzotech.utilisateur.dto.UtilisateurResponse;
import com.kodzotech.utilisateur.model.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UtilisateurMapper {

    @Autowired
    private EntiteClient entiteClient;

    public abstract Utilisateur dtoToEntity(UtilisateurDto utilisateurDto);

    @Mapping(target = "password", ignore = true)
    public abstract UtilisateurDto entityToDto(Utilisateur utilisateur);

    @Mapping(target = "entite", expression = "java(getEntite(utilisateur.getEntiteId()))")
    @Mapping(target = "password", ignore = true)
    public abstract UtilisateurResponse entityToResponse(Utilisateur utilisateur);

    @Mapping(target = "id", ignore = true)
    public abstract Utilisateur dtoToEntity(@MappingTarget Utilisateur utilisateurDB,
                                            UtilisateurDto utilisateurDto);

    EntiteDto getEntite(Long id) {
        if (id == null) return null;
        return entiteClient.getEntite(id);
    }
}
