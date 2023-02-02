package com.kodzotech.entite.service.impl;

import com.kodzotech.entite.client.AdresseClient;
import com.kodzotech.entite.dto.AdresseDto;
import com.kodzotech.entite.dto.EntiteDto;
import com.kodzotech.entite.dto.EntiteResponse;
import com.kodzotech.entite.mapper.EntiteMapper;
import com.kodzotech.entite.model.Entite;
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
    private final EntiteMapper entiteMapper;
    private final AdresseClient adresseClient;

    @Override
    @Transactional
    public void save(EntiteDto entiteDto) {
        Validate.notNull(entiteDto);
        Entite entite = entiteMapper.dtoToEntity(entiteDto);
        Entite parent = null;
        int niveau = 0;
        if (entiteDto.getParentId() != null) {
            parent = entiteRepository
                    .findById(entiteDto.getParentId())
                    .orElseThrow(() ->
                            new EntiteException("erreur.entite.parent.non.trouve"));
            //Mettre à jour le niveau
            niveau = parent.getNiveau() + 1;
            entite.setParent(parent);
        }
        entite.setNiveau(niveau);
        validerEntite(entite);
        AdresseDto adresseDto = entiteDto.getAdresse();
        //Update adresse
        Long adresseId = null;
        Long adresseLivraisonId = null;
        if (adresseDto != null) {
            if (adresseDto.getId() != null) {
                adresseId = adresseClient.update(adresseDto.getId(), adresseDto);
            } else {
                adresseId = adresseClient.save(adresseDto);
            }
            entite.setAdresseId(adresseId);
        }
        if (entite.getId() != null) {
            Entite entiteOriginal = entiteRepository
                    .findById(entite.getId()).get();
            entiteOriginal = entiteMapper.dtoToEntity(entiteOriginal, entite);
            entiteRepository.save(entiteOriginal);
        } else {
            entiteRepository.save(entite);
        }
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
            //vérification si la société existe
        }
        if (entite.getId() != null) {
            // Mode modification
            //Rechercher l'entite de la base
            Entite entiteOriginal = entiteRepository
                    .findById(entite.getId())
                    .orElseThrow(() ->
                            new EntiteException("erreur.entite.id.non.trouve"));

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
        return entiteMapper.entityToDto(entite);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntiteResponse> getAllEntitesBySociete(Long idSociete) {
        List<Entite> entiteList = entiteRepository.findAllBySocieteId(idSociete);
        return entiteMapper.entityToResponse(entiteList);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Entite entite = entiteRepository.findById(id)
                .orElseThrow(
                        () -> new EntiteException("erreur.entite.id.non.trouve"));
        entiteRepository.delete(entite);
    }
}
