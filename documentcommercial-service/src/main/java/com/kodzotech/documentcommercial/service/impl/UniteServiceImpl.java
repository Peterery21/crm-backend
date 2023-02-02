package com.kodzotech.documentcommercial.service.impl;

import com.kodzotech.documentcommercial.exception.UniteException;
import com.kodzotech.documentcommercial.repository.UniteRepository;
import com.kodzotech.documentcommercial.dto.UniteDto;
import com.kodzotech.documentcommercial.mapper.UniteMapper;
import com.kodzotech.documentcommercial.model.Unite;
import com.kodzotech.documentcommercial.service.ArticleService;
import com.kodzotech.documentcommercial.service.UniteService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniteServiceImpl implements UniteService {

    private final UniteRepository uniteRepository;
    private final UniteMapper uniteMapper;
    private final ArticleService articleService;


    @Override
    @Transactional
    public void save(UniteDto uniteDto) {
        Validate.notNull(uniteDto);
        Unite unite = uniteMapper.dtoToEntity(uniteDto);
        validerUnite(unite);
        if (unite.getId() != null) {
            Unite uniteOriginal = uniteRepository
                    .findById(unite.getId()).get();
            uniteOriginal = uniteMapper.dtoToEntity(uniteOriginal, unite);
            uniteRepository.save(uniteOriginal);
        } else {
            uniteRepository.save(unite);
        }
    }

    @Override
    public void validerUnite(Unite unite) {

        if (unite.getLibelle() == null || unite.getLibelle().isEmpty()) {
            throw new UniteException("erreur.unite.libelle.null");
        }

        if (unite.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            Unite uniteOriginal = uniteRepository
                    .findById(unite.getId())
                    .orElseThrow(() ->
                            new UniteException("erreur.unite.id.non.trouve"));

            //Vérifier si libellé en double
            Unite uniteTemp = uniteRepository
                    .findByLibelle(unite.getLibelle())
                    .orElse(null);
            if (uniteTemp != null) {
                if (uniteTemp.getId() != uniteOriginal.getId()) {
                    throw new UniteException("erreur.unite.libelle.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            Unite uniteTemp = uniteRepository
                    .findByLibelle(unite.getLibelle())
                    .orElse(null);
            if (uniteTemp != null) {
                throw new UniteException("erreur.unite.libelle.doublon");
            }
        }
    }


    @Override
    public UniteDto getUnite(Long id) {
        Unite unite = uniteRepository.findById(id)
                .orElseThrow(() -> new UniteException(
                        "erreur.unite.id.non.trouve"));
        return uniteMapper.entityToDto(unite);
    }

    @Override
    public List<UniteDto> getAllUnites() {
        List<Unite> uniteList = uniteRepository.findAll();
        return uniteList.stream()
                .map(uniteMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Unite unite = uniteRepository.findById(id)
                .orElseThrow(
                        () -> new UniteException("erreur.unite.id.non.trouve"));
        if (articleService.checkUsedUnite(id)) {
            throw new UniteException("erreur.unite.utilise.compte");
        }
        uniteRepository.delete(unite);
    }
}
