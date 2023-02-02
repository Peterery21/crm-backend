package com.kodzotech.documentcommercial.service;

import com.kodzotech.documentcommercial.dto.ArticleDto;
import com.kodzotech.documentcommercial.dto.ArticleResponse;
import com.kodzotech.documentcommercial.model.Article;

import java.util.List;

public interface ArticleService {

    /**
     * Enregistrer et modifier un article
     *
     * @param articleDto
     */
    void save(ArticleDto articleDto);

    /**
     * Valider les données de l'article
     *
     * @param article
     */
    void validerArticle(Article article);

    /**
     * Renvoyer l'article en fonction de son id
     *
     * @param id
     * @return
     */
    ArticleDto getArticle(Long id);

    /**
     * Renvoyer la liste des articles
     *
     * @return
     */
    List<ArticleDto> getAllArticle();

    /**
     * Supprimer un article
     *
     * @param id
     */
    void delete(Long id);

    /**
     * Vérifier si la catégorie est associé à un article
     *
     * @param id
     * @return
     */
    boolean checkUsedCategorie(Long id);

    /**
     * Vérifier si la marque est associé à un article
     *
     * @param id
     * @return
     */
    boolean checkUsedMarque(Long id);

    /**
     * Vérifier si l'unité est associé à un article
     *
     * @param id
     * @return
     */
    boolean checkUsedUnite(Long id);

    List<ArticleResponse> getAllArticle(Integer page, Integer size);

    /**
     * Renvoyer le nombre total d'article
     *
     * @return
     */
    Long getNbreArticle();

    /**
     * Récupérer les articles disponible pour l'achat
     *
     * @param societeId
     * @return
     */
    List<ArticleDto> getAllAchatArticle(Long societeId);

    /**
     * Récupérer les articles disponible pour la vente
     *
     * @param societeId
     * @return
     */
    List<ArticleDto> getAllVenteArticle(Long societeId);
}
