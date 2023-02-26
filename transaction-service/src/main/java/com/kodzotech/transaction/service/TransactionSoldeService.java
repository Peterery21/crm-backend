package com.kodzotech.transaction.service;

import com.kodzotech.transaction.dto.StatDto;

import java.time.LocalDate;
import java.util.List;

public interface TransactionSoldeService {

    /**
     * Calculer le solde d'un compte
     *
     * @param compteBancaireId
     * @param deviseId
     * @return
     */
    Double getSoldeCompteBancaire(Long compteBancaireId, Long deviseId);

    /**
     * Calculer le solde d'un compte
     *
     * @param compteBancaireId
     * @param date
     * @return
     */
    Double getSoldeCompteBancaireParDate(Long compteBancaireId, LocalDate date);

    /**
     * Récupérer le total de toutes les dépense
     *
     * @return
     */
    Double getTotalDepenses(Long deviseId);

    /**
     * Récupérer le total de toutes les recettes
     *
     * @return
     */
    Double getTotalRecette(Long deviseId);

    /**
     * Récupérer le solde total
     *
     * @return
     */
    Double getSoldeTotal(Long deviseId);

    /**
     * Récupérer la statistique des transactions
     *
     * @return
     */
    StatDto getStatTransaction();

    /**
     * Récupérer le montant d'un document
     *
     * @return
     */
    Double getTotalByDocument(Long documentId);
}
