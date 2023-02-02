package com.kodzotech.documentcommercial.service;

import com.kodzotech.documentcommercial.dto.CategorieArticleDto;
import com.kodzotech.documentcommercial.model.CategorieArticle;

import java.util.List;

public interface CategorieArticleService {

    /**
     * Enregistrer et modifier une catégorie
     *
     * @param categorieArticleDto
     */
    void save(CategorieArticleDto categorieArticleDto);

    /**
     * Valider les données de la catégorie
     *
     * @param categorieArticle
     */
    void validerCategorieArticle(CategorieArticle categorieArticle);

    /**
     * Renvoyer la catégorie en fonction de son id
     *
     * @param id
     * @return
     */
    CategorieArticleDto getCategorieArticle(Long id);

    /**
     * Renvoyer la liste des catégories
     *
     * @param societeId
     * @return
     */
    List<CategorieArticleDto> getAllCategorieArticlesBySociete(Long societeId);

    /**
     * Supprimer une catégorie article
     *
     * @param id
     */
    void delete(Long id);
}
