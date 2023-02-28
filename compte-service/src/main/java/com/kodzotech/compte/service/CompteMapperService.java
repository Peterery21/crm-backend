package com.kodzotech.compte.service;

import com.kodzotech.compte.client.ResponsableClient;
import com.kodzotech.compte.dto.*;
import com.kodzotech.compte.mapper.CategorieCompteMapper;
import com.kodzotech.compte.mapper.CompteMapper;
import com.kodzotech.compte.mapper.TailleMapper;
import com.kodzotech.compte.model.CategorieCompte;
import com.kodzotech.compte.model.Compte;
import com.kodzotech.compte.model.Taille;
import com.kodzotech.compte.repository.CategorieCompteRepository;
import com.kodzotech.compte.repository.TailleRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompteMapperService {

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
    @Autowired
    private CompteMapper compteMapper;

    public Compte dtoToEntity(CompteDto compteDto){
        Compte compte = compteMapper.dtoToEntity(compteDto);
        compte.setCategorieCompte(getCategorie(compteDto));
        compte.setTaille(getTaille(compteDto));
        return compte;
    }
    public Compte dtoToEntity(Compte compteOriginal, Compte compteModifie) {
        return compteMapper.dtoToEntity(compteOriginal, compteModifie);
    }
    public List<CompteDto> entitiesToDto(List<Compte> comptes) {
        List<Long> adresseIds = comptes.stream().map(c -> c.getAdresseId()).collect(Collectors.toList());
        adresseIds.addAll(comptes.stream().filter(c -> c.getAdresseLivraisonId() != null).map(c -> c.getAdresseLivraisonId()).collect(Collectors.toList()));
        List<AdresseDto> adresseDtoList = adresseIds.size() > 0 ? adresseService.getAdressesById(adresseIds) : new ArrayList<>();

        return comptes.stream()
                .map(compte -> {
                    CompteDto compteDto = compteMapper.entityToDto(compte);
                    compteDto.setAdresse(getAdresse(adresseDtoList, compte.getAdresseId()));
                    compteDto.setAdresseLivraison(getAdresse(adresseDtoList, compte.getAdresseLivraisonId()));
                    return compteDto;
                }).collect(Collectors.toList());
    }

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
                .map(compte -> {
                    CompteResponse compteResponse = compteMapper.entityToResponse(compte);
                    compteResponse.setCategorie(categorieCompteMapper.entityToDto(compte.getCategorieCompte()));
                    compteResponse.setTaille(tailleMapper.entityToDto(compte.getTaille()));
                    compteResponse.setResponsable(getResponsable(responsableDtoList, compte.getResponsableId()));
                    compteResponse.setAdresse(getAdresse(adresseDtoList, compte.getAdresseId()));
                    compteResponse.setAdresseLivraison(getAdresse(adresseDtoList, compte.getAdresseLivraisonId()));
                    compteResponse.setSecteurActivite(getSecteurActivite(secteurActiviteDtoList, compte.getSecteurActiviteId()));
                    return compteResponse;
                })
                .collect(Collectors.toList());
    }

    public CompteDto entityToDto(Compte compte) {
        List<AdresseDto> adresseDtoList = adresseService.getAdressesById(Arrays.asList(compte.getAdresseId(),
                compte.getAdresseLivraisonId()));
        CompteDto compteDto = compteMapper.entityToDto(compte);
        compteDto.setAdresse(getAdresse(adresseDtoList, compte.getAdresseId()));
        compteDto.setAdresseLivraison(getAdresse(adresseDtoList, compte.getAdresseLivraisonId()));
        return compteDto;
    }

    private CategorieCompte getCategorie(CompteDto compteDto) {
        CategorieCompte categorieCompte = null;
        if (compteDto.getCategorieCompteId() != null) {
            categorieCompte =
                    categorieCompteRepository
                            .findById(compteDto.getCategorieCompteId())
                            .orElse(null);
        }
        return categorieCompte;
    }

    private Taille getTaille(CompteDto compteDto) {
        Taille taille = null;
        if (compteDto.getTailleId() != null) {
            taille =
                    tailleRepository
                            .findById(compteDto.getTailleId())
                            .orElse(null);
        }
        return taille;
    }

    private SecteurActiviteDto getSecteurActivite(List<SecteurActiviteDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(new SecteurActiviteDto());
    }

    private AdresseDto getAdresse(List<AdresseDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(new AdresseDto());
    }

    private ResponsableDto getResponsable(List<ResponsableDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(new ResponsableDto());
    }
}
