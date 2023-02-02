package com.kodzotech.documentcommercial.service;

import com.kodzotech.documentcommercial.dto.CategorieArticleResponse;

import java.util.List;

public interface CategorieArticleChartService {

    /**
     * Renvoie la liste des entités d'une societe par niveau
     *
     * @return
     */
    List<CategorieArticleResponse> getAllCategorieArticlesBySociete();

    /**
     * Récupérer les enfants d'une entité de façon récursive
     *
     * @param categorieArticleResponse
     * @param categorieArticleResponses
     * @return
     */
    List<CategorieArticleResponse> getCategorieArticleChild(CategorieArticleResponse categorieArticleResponse, List<CategorieArticleResponse> categorieArticleResponses);

    /**
     * Vérifie si une entité à des fils
     *
     * @param categorieArticleId
     * @return
     */
    Boolean hasChild(Long categorieArticleId);

    /**
     * Récupérer la liste des id des categorieArticles fils + le parent
     *
     * @param categorieArticleId
     * @return
     */
    List<Long> getChildsId(Long categorieArticleId);

    /**
     * Récupérer les enfants d'un parent
     *
     * @param parentId
     * @param categorieArticleResponses
     * @return
     */
    List<CategorieArticleResponse> getParentChild(Long parentId, List<CategorieArticleResponse> categorieArticleResponses);
}
