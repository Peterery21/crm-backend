package com.kodzotech.compte.service;

import com.kodzotech.compte.dto.CategorieCompteDto;
import com.kodzotech.compte.model.CategorieCompte;

import java.util.List;

public interface CategorieCompteService {

    /**
     * Enregistrer et modifier une catégorie
     *
     * @param categorieCompteDto
     */
    void save(CategorieCompteDto categorieCompteDto);

    /**
     * Valider les données de la catégorie
     *
     * @param categorieCompte
     */
    void validerCategorieCompte(CategorieCompte categorieCompte);

    /**
     * Renvoyer la catégorie en fonction de son id
     *
     * @param id
     * @return
     */
    CategorieCompteDto getCategorieCompte(Long id);

    /**
     * Renvoyer la liste des catégories
     *
     * @return
     */
    List<CategorieCompteDto> getAllCategoriesCompte();

    /**
     * Supprimer une catégorie compte
     *
     * @param id
     */
    void delete(Long id);
}
