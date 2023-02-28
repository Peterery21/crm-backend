package com.kodzotech.compte.mapper;

import com.kodzotech.compte.client.ResponsableClient;
import com.kodzotech.compte.dto.*;
import com.kodzotech.compte.model.CategorieCompte;
import com.kodzotech.compte.model.Compte;
import com.kodzotech.compte.model.Taille;
import com.kodzotech.compte.repository.CategorieCompteRepository;
import com.kodzotech.compte.repository.TailleRepository;
import com.kodzotech.compte.service.AdresseService;
import com.kodzotech.compte.service.SecteurActiviteService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompteMapper {

    public Compte dtoToEntity(CompteDto compteDto);

    /**
     * Permet de renseigner les nouvelles données à modifier
     *
     * @param compteOriginal : compte provenant de la base
     * @param compteModifie  : compte avec les informations à modifier
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entiteId", ignore = true)
    @Mapping(target = "societeId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public Compte dtoToEntity(@MappingTarget Compte compteOriginal, Compte compteModifie);


    @Mappings({
            @Mapping(target = "categorieCompteId", source = "compte.categorieCompte.id"),
    })
    public CompteDto entityToDto(Compte compte);

    public CompteResponse entityToResponse(Compte compte);


}
