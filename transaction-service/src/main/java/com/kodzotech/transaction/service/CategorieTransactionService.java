package com.kodzotech.transaction.service;

import com.kodzotech.transaction.dto.CategorieTransactionDto;
import com.kodzotech.transaction.model.CategorieTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategorieTransactionService {

    /**
     * Enregistrer une catégorie
     * @param categorieTransaction
     */
    @Transactional
    void save(CategorieTransaction categorieTransaction);

    /**
     * Créer une catégorie de type dépense et appelé save pour enregistrer
     * @param categorieTransactionDto
     */
    void saveCategorieDepense(CategorieTransactionDto categorieTransactionDto);

    /**
     * Créer une catégorie de type recette et appelé save pour enregistrer
     * @param categorieTransactionDto
     */
    void saveCategorieRecette(CategorieTransactionDto categorieTransactionDto);

    /**
     * Modifier une catégorie
     * @param categorieTransactionDto
     */
    @Transactional
    void update(CategorieTransactionDto categorieTransactionDto);

    /**
     * Vérifier les champs de la catégorie
     * @param categorieTransaction
     */
    void verifierCategorie(CategorieTransaction categorieTransaction);

    /**
     * Récupérer une catégorie
     * @param id
     * @return
     */
    CategorieTransactionDto getCategorie(Long id);

    /**
     * Récuperer toutes les catégories
     * @return
     */
    List<CategorieTransactionDto> getAllCategories();

    /**
     * Renvoyer les catégories par type
     * @param type
     * @return
     */
    List<CategorieTransactionDto> getAllCategoriesByType(String type);

    /**
     * Chercher une catégorie par le code
     * @param code
     * @return
     */
    CategorieTransactionDto getCategorieByCode(String code);

    /**
     * Supprimer une catégorie
     * @param id
     */
    void supprimer(Long id);
}
