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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CompteMapper {

    @Autowired
    private CategorieCompteRepository categorieCompteRepository;
    @Autowired
    private TailleRepository tailleRepository;
    @Autowired
    private SecteurActiviteService secteurActiviteService;
    @Autowired
    public CategorieCompteMapper categorieCompteMapper;
    @Autowired
    public TailleMapper tailleMapper;
    @Autowired
    private AdresseService adresseService;
    @Autowired
    private ResponsableClient responsableClient;

    @Mapping(target = "categorieCompte", expression = "java(getCategorie(compteDto))")
    @Mapping(target = "taille", expression = "java(getTaille(compteDto))")
    public abstract Compte dtoToEntity(CompteDto compteDto);

    CategorieCompte getCategorie(CompteDto compteDto) {
        CategorieCompte categorieCompte = null;
        if (compteDto.getCategorieCompteId() != null) {
            categorieCompte =
                    categorieCompteRepository
                            .findById(compteDto.getCategorieCompteId())
                            .orElse(null);
        }
        return categorieCompte;
    }

    Taille getTaille(CompteDto compteDto) {
        Taille taille = null;
        if (compteDto.getTailleId() != null) {
            taille =
                    tailleRepository
                            .findById(compteDto.getTailleId())
                            .orElse(null);
        }
        return taille;
    }

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
    public abstract Compte dtoToEntity(@MappingTarget Compte compteOriginal, Compte compteModifie);

    public CompteDto entityToDto(Compte compte) {
        List<AdresseDto> adresseDtoList = adresseService.getAdressesById(Arrays.asList(compte.getAdresseId(), compte.getAdresseLivraisonId()));
        return entityToDto(compte, adresseDtoList);
    }

    @Mapping(target = "adresse", expression = "java(getAdresse(adresseDtoList, compte.getAdresseId()))")
    @Mapping(target = "adresseLivraison", expression = "java(getAdresse(adresseDtoList, compte.getAdresseLivraisonId()))")
    public abstract CompteDto entityToDto(Compte compte, List<AdresseDto> adresseDtoList);

    public List<CompteDto> entitiesToDto(List<Compte> comptes) {
        List<Long> adresseIds = comptes.stream().map(c -> c.getAdresseId()).collect(Collectors.toList());
        adresseIds.addAll(comptes.stream().filter(c -> c.getAdresseLivraisonId() != null).map(c -> c.getAdresseLivraisonId()).collect(Collectors.toList()));
        List<AdresseDto> adresseDtoList = adresseIds.size() > 0 ? adresseService.getAdressesById(adresseIds) : new ArrayList<>();

        return comptes.stream()
                .map(compte -> entityToDto(compte, adresseDtoList)).collect(Collectors.toList());
    }

    @Mapping(target = "categorie", expression = "java(categorieCompteMapper.entityToDto(compte.getCategorieCompte()))")
    @Mapping(target = "taille", expression = "java(tailleMapper.entityToDto(compte.getTaille()))")
    @Mapping(target = "responsable", expression = "java(getResponsable(responsableDtoList, compte.getResponsableId()))")
    @Mapping(target = "adresse", expression = "java(getAdresse(adresseDtoList, compte.getAdresseId()))")
    @Mapping(target = "adresseLivraison", expression = "java(getAdresse(adresseDtoList, compte.getAdresseLivraisonId()))")
    @Mapping(target = "secteurActivite", expression = "java(getSecteurActivite(secteurActiviteDtoList, compte.getSecteurActiviteId()))")
    public abstract CompteResponse entityToResponse(Compte compte,
                                                    List<SecteurActiviteDto> secteurActiviteDtoList,
                                                    List<AdresseDto> adresseDtoList,
                                                    List<ResponsableDto> responsableDtoList);


    public List<CompteResponse> entitiesToResponse(List<Compte> compteList) {

        //Récuperer les id des secteurs activite
        List<Long> secteurActiviteIds = compteList.stream().filter(c -> c.getSecteurActiviteId() != null).map(t -> t.getSecteurActiviteId()).distinct().collect(Collectors.toList());
        //Faire un appel au service secteurActivite pour avoir les secteurs des ids
        List<SecteurActiviteDto> secteurActiviteDtoList = secteurActiviteIds.size() > 0 ? secteurActiviteService.getSecteurActivitesById(secteurActiviteIds) : new ArrayList<>();

        //Adresse
        List<Long> adresseIds = compteList.stream()
                .filter(c -> c.getAdresseId() != null)
                .map(c -> c.getAdresseId()).collect(Collectors.toList());
        adresseIds.addAll(compteList.stream()
                .filter(c -> c.getAdresseLivraisonId() != null)
                .map(c -> c.getAdresseLivraisonId()).collect(Collectors.toList()));
        List<AdresseDto> adresseDtoList = adresseIds.size() > 0 ? adresseService.getAdressesById(adresseIds) : new ArrayList<>();

        //Responsable
        List<Long> responsableIds = compteList.stream()
                .filter(c -> c.getResponsableId() != null)
                .map(c -> c.getResponsableId()).collect(Collectors.toList());
        List<ResponsableDto> responsableDtoList = responsableIds.size() > 0 ? responsableClient.getResponsablesById(responsableIds) : new ArrayList<>();


        return compteList.stream()
                .map(compte -> entityToResponse(compte, secteurActiviteDtoList, adresseDtoList, responsableDtoList))
                .collect(Collectors.toList());
    }


    SecteurActiviteDto getSecteurActivite(List<SecteurActiviteDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(new SecteurActiviteDto());
    }

    AdresseDto getAdresse(List<AdresseDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(new AdresseDto());
    }

    ResponsableDto getResponsable(List<ResponsableDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(new ResponsableDto());
    }
}
