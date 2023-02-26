package com.kodzotech.entite.service.impl;

import com.kodzotech.entite.client.AdresseClient;
import com.kodzotech.entite.dto.AdresseDto;
import com.kodzotech.entite.dto.EntiteDto;
import com.kodzotech.entite.dto.EntiteResponse;
import com.kodzotech.entite.model.Entite;
import com.kodzotech.entite.repository.SocieteRepository;
import com.kodzotech.entite.service.EntiteMapperService;
import com.kodzotech.entite.service.EntiteService;
import com.kodzotech.entite.exception.EntiteException;
import com.kodzotech.entite.repository.EntiteRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntiteServiceImpl implements EntiteService {

    private final EntiteRepository entiteRepository;
    private final EntiteMapperService entiteMapperService;
    private final AdresseClient adresseClient;
    private final SocieteRepository societeRepository;

    @Override
    @Transactional
    public void save(EntiteDto entiteDto) {
        Validate.notNull(entiteDto);
        Entite entite = entiteMapperService.dtoToEntity(entiteDto);
        int niveau = 0;
        if (entiteDto.getParentId() != null) {
            Entite parent = entiteRepository
                    .findById(entiteDto.getParentId())
                    .orElseThrow(() ->
                            new EntiteException("erreur.entite.parent.non.trouve"));
            //Mettre à jour le niveau
            niveau = parent.getNiveau() + 1;
            entite.setParent(parent);
        }
        entite.setNiveau(niveau);
        validerEntite(entite);
        entite.setAdresseId(saveAdresse(entiteDto.getAdresse()));
        if (entite.getId() != null) {
            Entite entiteOriginal = entiteRepository
                    .findById(entite.getId()).get();
            entiteOriginal = entiteMapperService.dtoToEntity(entiteOriginal, entite);
            entiteRepository.save(entiteOriginal);
        } else {
            entiteRepository.save(entite);
        }
    }

    private Long saveAdresse(AdresseDto adresseDto){
        //Update adresse
        Long adresseId = null;
        if (adresseDto != null) {
            if (adresseDto.getId() != null) {
                adresseId = adresseClient.update(adresseDto.getId(), adresseDto);
            } else {
                adresseId = adresseClient.save(adresseDto);
            }
        }
        return adresseId;
    }

    @Override
    @Transactional(readOnly = true)
    public void validerEntite(Entite entite) {

        if (entite.getNom() == null || entite.getNom().isEmpty()) {
            throw new EntiteException("erreur.entite.nom.null");
        }

        if (entite.getSocieteId() == null) {
            throw new EntiteException("erreur.entite.societeId.null");
        } else {
            //TODO vérification si la société existe
            if (!societeRepository.existsById(entite.getSocieteId()))
                    throw new EntiteException(
                            "erreur.entite.societeId.null");
        }

        if (entite.getId() != null) {
            // Mode modification
            //Rechercher l'entite de la base
            Entite entiteOriginal = entiteRepository
                    .findById(entite.getId())
                    .orElseThrow(() -> new EntiteException(
                                    "erreur.entite.id.non.trouve"));

            //Vérifier si libellé en double
            Entite entiteTemp = entiteRepository
                    .findByNomAndSocieteId(entite.getNom(), entite.getSocieteId())
                    .orElse(null);
            if (entiteTemp != null) {
                if (entiteTemp.getId() != entiteOriginal.getId()) {
                    throw new EntiteException("erreur.entite.nom.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            Entite entiteTemp = entiteRepository
                    .findByNomAndSocieteId(entite.getNom(), entite.getSocieteId())
                    .orElse(null);
            if (entiteTemp != null) {
                throw new EntiteException("erreur.entite.nom.doublon");
            }
        }
    }


    @Override
    @Transactional(readOnly = true)
    public EntiteDto getEntite(Long id) {
        Entite entite = entiteRepository.findById(id)
                .orElseThrow(() -> new EntiteException(
                        "erreur.entite.id.non.trouve"));
        EntiteDto entiteDto =  entiteMapperService.entityToDto(entite);
        entiteDto.setAdresse(adresseClient.getAdresse(entite.getAdresseId()));
        return entiteDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntiteResponse> getAllEntitesBySociete(Long idSociete) {
        List<Entite> entiteList = entiteRepository.findAllBySocieteId(idSociete);
        return entiteMapperService.entityToResponse(entiteList);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Entite entite = entiteRepository.findById(id)
                .orElseThrow(() -> new EntiteException(
                                "erreur.entite.id.non.trouve"));
        entiteRepository.delete(entite);
    }
}
