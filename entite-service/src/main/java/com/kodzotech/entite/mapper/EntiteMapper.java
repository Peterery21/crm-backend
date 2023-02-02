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
public abstract class EntiteMapper {

    @Autowired
    private AdresseClient adresseClient;
    @Autowired
    private EntiteRepository entiteRepository;
    @Autowired
    private SocieteRepository societeRepository;
    @Autowired
    private SocieteMapper societeMapper;

    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "adresse", expression = "java(getAdresseById(entite.getAdresseId()))")
    public abstract EntiteDto entityToDto(Entite entite);

    @Mapping(target = "parentId", source = "parent.id")
    public abstract EntiteDto entityToDtoNoAdresse(Entite entite);

    public abstract Entite dtoToEntity(EntiteDto entiteDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent.id", source = "parent.id")
    public abstract Entite dtoToEntity(@MappingTarget Entite entiteOriginal, Entite entiteModifie);

    @Mapping(target = "parent", expression = "java(entityToDtoNoAdresse(entite.getParent()))")
    @Mapping(target = "adresse", expression = "java(getAdresse(adresses, entite.getAdresseId()))")
    @Mapping(target = "societe", expression = "java(getSociete(societes, entite.getSocieteId()))")
    public abstract EntiteResponse entityToResponse(Entite entite,
                                                    List<AdresseDto> adresses,
                                                    List<SocieteDto> societes);

    public List<EntiteResponse> entityToResponse(List<Entite> entites) {
        //Récupérer l'adresse
        List<Long> adresseIds = entites.stream().map(c -> c.getAdresseId()).collect(Collectors.toList());
        List<Long> societeIds = entites.stream().map(c -> c.getSocieteId()).collect(Collectors.toList());

        List<AdresseDto> adresseDtoList = adresseIds.size() > 0 ? adresseClient.getAdressesById(adresseIds) : new ArrayList<>();
        //Société
        List<SocieteDto> societeDtoList = societeRepository.findAllById(societeIds)
                .stream()
                .map(societeMapper::entityToDto)
                .collect(Collectors.toList());

        return entites.stream()
                .map(entite -> entityToResponse(entite, adresseDtoList, societeDtoList))
                .collect(Collectors.toList());
    }


    AdresseDto getAdresse(List<AdresseDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }

    SocieteDto getSociete(List<SocieteDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }

    AdresseDto getAdresseById(Long adresseId) {
        if (adresseId == null) return null;
        List<AdresseDto> adresseDtoList = adresseClient.getAdressesById(Arrays.asList(adresseId));
        if (!adresseDtoList.isEmpty()) {
            return adresseDtoList.get(0);
        } else {
            return null;
        }
    }
}
