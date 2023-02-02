package com.kodzotech.documentcommercial.service.impl;

import com.kodzotech.documentcommercial.dto.CategorieArticleDto;
import com.kodzotech.documentcommercial.exception.CategorieArticleException;
import com.kodzotech.documentcommercial.repository.CategorieArticleRepository;
import com.kodzotech.documentcommercial.mapper.CategorieArticleMapper;
import com.kodzotech.documentcommercial.model.CategorieArticle;
import com.kodzotech.documentcommercial.service.ArticleService;
import com.kodzotech.documentcommercial.service.CategorieArticleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieArticleServiceImpl implements CategorieArticleService {

    private final CategorieArticleRepository categorieArticleRepository;
    private final CategorieArticleMapper categorieArticleMapper;
    private final ArticleService articleService;

    @Override
    @Transactional
    public void save(CategorieArticleDto categorieArticleDto) {
        Validate.notNull(categorieArticleDto);
        CategorieArticle categorieArticle = categorieArticleMapper.dtoToEntity(categorieArticleDto);
        validerCategorieArticle(categorieArticle);
        int niveau = 0;
        if (categorieArticle.getParent() != null) {
            niveau = categorieArticle.getParent().getNiveau() + 1;
        }
        categorieArticle.setNiveau(niveau);
        if (categorieArticle.getId() != null) {
            CategorieArticle categorieArticleOriginal = categorieArticleRepository
                    .findById(categorieArticle.getId()).get();
            categorieArticleOriginal = categorieArticleMapper.dtoToEntity(categorieArticleOriginal, categorieArticle);
            categorieArticleRepository.save(categorieArticleOriginal);
        } else {
            categorieArticleRepository.save(categorieArticle);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void validerCategorieArticle(CategorieArticle categorieArticle) {

        if (categorieArticle.getLibelle() == null || categorieArticle.getLibelle().isEmpty()) {
            throw new CategorieArticleException("erreur.categorieArticle.libelle.null");
        }

        if (categorieArticle.getNiveau() > 1 && categorieArticle.getParent() == null) {
            throw new CategorieArticleException("erreur.categorieArticle.parent.non.trouve");
        }

        if (categorieArticle.getId() != null) {
            // Mode modification
            //Rechercher l'categorieArticle de la base
            CategorieArticle categorieArticleOriginal = categorieArticleRepository
                    .findById(categorieArticle.getId())
                    .orElseThrow(() ->
                            new CategorieArticleException("erreur.categorieArticle.id.non.trouve"));

            //Vérifier si libellé en double
            CategorieArticle categorieArticleTemp = categorieArticleRepository
                    .findByLibelleAndSocieteId(categorieArticle.getLibelle(), categorieArticle.getSocieteId())
                    .orElse(null);
            if (categorieArticleTemp != null) {
                if (categorieArticleTemp.getId() != categorieArticleOriginal.getId()) {
                    throw new CategorieArticleException("erreur.categorieArticle.libelle.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            CategorieArticle categorieArticleTemp = categorieArticleRepository
                    .findByLibelleAndSocieteId(categorieArticle.getLibelle(), categorieArticle.getSocieteId())
                    .orElse(null);
            if (categorieArticleTemp != null) {
                throw new CategorieArticleException("erreur.categorieArticle.libelle.doublon");
            }
        }
    }


    @Override
    @Transactional(readOnly = true)
    public CategorieArticleDto getCategorieArticle(Long id) {
        CategorieArticle categorieArticle = categorieArticleRepository.findById(id)
                .orElseThrow(() -> new CategorieArticleException(
                        "erreur.categorieArticle.id.non.trouve"));
        return categorieArticleMapper.entityToDto(categorieArticle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorieArticleDto> getAllCategorieArticlesBySociete(Long idSociete) {
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAllBySocieteId(idSociete);
        return categorieArticleList.stream().map(categorieArticleMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CategorieArticle categorieArticle = categorieArticleRepository.findById(id)
                .orElseThrow(
                        () -> new CategorieArticleException("erreur.categorieArticle.id.non.trouve"));
        if (articleService.checkUsedCategorie(id)) {
            throw new CategorieArticleException("erreur.categorieArticle.utilise.article");
        }
        categorieArticleRepository.delete(categorieArticle);
    }
}
