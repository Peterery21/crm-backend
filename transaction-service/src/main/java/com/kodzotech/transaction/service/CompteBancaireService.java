package com.kodzotech.transaction.service;

import com.kodzotech.transaction.dto.CompteBancaireDto;
import com.kodzotech.transaction.dto.CompteBancaireResponse;
import com.kodzotech.transaction.model.CompteBancaire;
import com.kodzotech.transaction.model.CompteBancaireType;

import java.util.List;

public interface CompteBancaireService {

    /**
     * Créer un compte bancaire
     *
     * @param compteBancaireDto
     */
    void save(CompteBancaireDto compteBancaireDto);

    /**
     * Valider le compte bancaire
     *
     * @param compteBancaire
     */
    void validerCompteBancaire(CompteBancaire compteBancaire);

    /**
     * Récuperer le compte en fonction de l'id
     *
     * @param id
     * @return
     */
    CompteBancaireDto getCompteBancaire(Long id);

    /**
     * Renvoyer la liste des comptes bancaires
     *
     * @return
     */
    List<CompteBancaireResponse> getAllCompteBancaire();

    /**
     * Supprimer un compte bancaire
     *
     * @param id
     */
    void supprimer(Long id);

    /**
     * Récupérer la liste en fonction des ids
     *
     * @param ids
     * @return
     */
    List<CompteBancaireDto> getCompteBancairesById(List<Long> ids);

    /**
     * Renvoie la liste des comptes bancaire par type
     *
     * @param type
     * @return
     */
    List<CompteBancaireDto> getCompteBancaireParType(CompteBancaireType type);
}
