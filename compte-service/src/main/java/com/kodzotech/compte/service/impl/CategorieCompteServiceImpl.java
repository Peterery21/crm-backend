package com.kodzotech.compte.service.impl;

import com.kodzotech.compte.dto.CategorieCompteDto;
import com.kodzotech.compte.exception.CategorieCompteException;
import com.kodzotech.compte.mapper.CategorieCompteMapper;
import com.kodzotech.compte.model.CategorieCompte;
import com.kodzotech.compte.repository.CategorieCompteRepository;
import com.kodzotech.compte.service.CategorieCompteService;
import com.kodzotech.compte.service.CompteService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieCompteServiceImpl implements CategorieCompteService {

    private final CategorieCompteRepository categorieCompteRepository;
    private final CategorieCompteMapper categorieCompteMapper;
    private final CompteService compteService;


    @Override
    @Transactional
    public void save(CategorieCompteDto categorieCompteDto) {
        Validate.notNull(categorieCompteDto);
        CategorieCompte categorieCompte = categorieCompteMapper.dtoToEntity(categorieCompteDto);
        validerCategorieCompte(categorieCompte);
        if (categorieCompte.getId() != null) {
            CategorieCompte categorieCompteOriginal = categorieCompteRepository
                    .findById(categorieCompte.getId()).get();
            categorieCompteOriginal = categorieCompteMapper.entityToDto(categorieCompteOriginal, categorieCompte);
            categorieCompteRepository.save(categorieCompteOriginal);
        } else {
            categorieCompteRepository.save(categorieCompte);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void validerCategorieCompte(CategorieCompte categorieCompte) {

        if (categorieCompte.getLibelle() == null || categorieCompte.getLibelle().isEmpty()) {
            throw new CategorieCompteException("erreur.categorieCompte.libelle.null");
        }

        if (categorieCompte.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            CategorieCompte categorieCompteOriginal = categorieCompteRepository
                    .findById(categorieCompte.getId())
                    .orElseThrow(() ->
                            new CategorieCompteException("erreur.categorieCompte.id.non.trouve"));

            //Vérifier si libellé en double
            CategorieCompte categorieCompteTemp = categorieCompteRepository
                    .findByLibelle(categorieCompte.getLibelle())
                    .orElse(null);
            if (categorieCompteTemp != null) {
                if (categorieCompteTemp.getId() != categorieCompteOriginal.getId()) {
                    throw new CategorieCompteException("erreur.categorieCompte.libelle.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            CategorieCompte categorieCompteTemp = categorieCompteRepository
                    .findByLibelle(categorieCompte.getLibelle())
                    .orElse(null);
            if (categorieCompteTemp != null) {
                throw new CategorieCompteException("erreur.categorieCompte.libelle.doublon");
            }
        }
    }


    @Override
    @Transactional(readOnly = true)
    public CategorieCompteDto getCategorieCompte(Long id) {
        CategorieCompte categorieCompte = categorieCompteRepository.findById(id)
                .orElseThrow(() -> new CategorieCompteException(
                        "erreur.categorieCompte.id.non.trouve"));
        return categorieCompteMapper.entityToDto(categorieCompte);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorieCompteDto> getAllCategoriesCompte() {
        List<CategorieCompte> categorieCompteList = categorieCompteRepository.findAll();
        return categorieCompteList.stream()
                .map(categorieCompteMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CategorieCompte categorieCompte = categorieCompteRepository.findById(id)
                .orElseThrow(
                        () -> new CategorieCompteException("erreur.categorieCompte.id.non.trouve"));
        if (compteService.checkUsedCategorie(id)) {
            throw new CategorieCompteException("erreur.categorieCompte.utilise.compte");
        }
        categorieCompteRepository.delete(categorieCompte);
    }
}
