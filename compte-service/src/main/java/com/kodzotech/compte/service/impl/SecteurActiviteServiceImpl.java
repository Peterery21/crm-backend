package com.kodzotech.compte.service.impl;

import com.kodzotech.compte.dto.SecteurActiviteDto;
import com.kodzotech.compte.exception.SecteurActiviteException;
import com.kodzotech.compte.mapper.SecteurActiviteMapper;
import com.kodzotech.compte.model.SecteurActivite;
import com.kodzotech.compte.repository.CompteRepository;
import com.kodzotech.compte.repository.SecteurActiviteRepository;
import com.kodzotech.compte.service.SecteurActiviteService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecteurActiviteServiceImpl implements SecteurActiviteService {

    private final SecteurActiviteRepository secteurActiviteRepository;
    private final SecteurActiviteMapper secteurActiviteMapper;
    private final CompteRepository compteRepository;

    @Override
    @Transactional
    public void save(SecteurActiviteDto secteurActiviteDto) {
        Validate.notNull(secteurActiviteDto);
        SecteurActivite secteurActivite = secteurActiviteMapper.dtoToEntity(secteurActiviteDto);
        validerSecteurActivite(secteurActivite);
        if (secteurActivite.getId() != null) {
            SecteurActivite secteurActiviteOriginal = secteurActiviteRepository
                    .findById(secteurActivite.getId()).get();
            secteurActiviteOriginal.setLibelle(secteurActivite.getLibelle());
            secteurActiviteRepository.save(secteurActiviteOriginal);
        } else {
            secteurActiviteRepository.save(secteurActivite);
        }
    }

    @Override
    public void validerSecteurActivite(SecteurActivite secteurActivite) {

        if (secteurActivite.getLibelle() == null || secteurActivite.getLibelle().isEmpty()) {
            throw new SecteurActiviteException("erreur.secteurActivite.libelle.null");
        }

        if (secteurActivite.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            SecteurActivite secteurActiviteOriginal = secteurActiviteRepository
                    .findById(secteurActivite.getId())
                    .orElseThrow(() ->
                            new SecteurActiviteException("erreur.secteurActivite.id.non.trouve"));

            //Vérifier si libellé en double
            SecteurActivite secteurActiviteTemp = secteurActiviteRepository
                    .findByLibelle(secteurActivite.getLibelle())
                    .orElse(null);
            if (secteurActiviteTemp != null) {
                if (secteurActiviteTemp.getId() != secteurActiviteOriginal.getId()) {
                    throw new SecteurActiviteException("erreur.secteurActivite.libelle.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            SecteurActivite secteurActiviteTemp = secteurActiviteRepository
                    .findByLibelle(secteurActivite.getLibelle())
                    .orElse(null);
            if (secteurActiviteTemp != null) {
                throw new SecteurActiviteException("erreur.secteurActivite.libelle.doublon");
            }
        }
    }


    @Override
    public SecteurActiviteDto getSecteurActivite(Long id) {
        if (id == null) throw new SecteurActiviteException(
                "erreur.secteurActivite.id.non.valide");
        SecteurActivite secteurActivite = secteurActiviteRepository.findById(id)
                .orElseThrow(() -> new SecteurActiviteException(
                        "erreur.secteurActivite.id.non.trouve"));
        return secteurActiviteMapper.entityToDto(secteurActivite);
    }

    @Override
    public List<SecteurActiviteDto> getAllCategoriesCompte() {
        List<SecteurActivite> secteurActiviteList = secteurActiviteRepository.findAll();
        return secteurActiviteList.stream()
                .map(secteurActiviteMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SecteurActivite secteurActivite = secteurActiviteRepository.findById(id)
                .orElseThrow(
                        () -> new SecteurActiviteException("erreur.secteurActivite.id.non.trouve"));
        if (compteRepository.existsBySecteurActiviteId(id)) {
            throw new SecteurActiviteException("erreur.secteurActivite.utilise.compte");
        }
        secteurActiviteRepository.delete(secteurActivite);
    }

    @Override
    public List<SecteurActiviteDto> getSecteurActivitesById(List<Long> ids) {

        return secteurActiviteRepository.findAllByIdIn(ids)
                .stream()
                .map(secteurActiviteMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
