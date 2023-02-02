package com.kodzotech.transaction.service;

import com.kodzotech.transaction.dto.TransactionDto;
import com.kodzotech.transaction.dto.TransactionResponse;
import com.kodzotech.transaction.model.Transaction;

import java.util.List;

public interface TransactionService {

    /**
     * Enregistrer une transaction
     *
     * @param transaction
     */
    void save(Transaction transaction);

    /**
     * Modifier une transaction
     *
     * @param transactionDto
     */
    void update(TransactionDto transactionDto);

    /**
     * Vérifier les champs de l'opération
     *
     * @param transaction
     */
    void verifierTransaction(Transaction transaction);

    /**
     * Annuler un transaction en fonction de la référence
     *
     * @param reference
     */
    void annulerTransaction(String reference);

    /**
     * Enregistrer une transaction de dépense
     *
     * @param transactionDto
     */
    void effectuerDepense(TransactionDto transactionDto);

    /**
     * Enregistrer une transaction de recette
     *
     * @param transactionDto
     */
    void effectuerRecette(TransactionDto transactionDto);

    /**
     * Récupérer la liste de toutes les transactions
     *
     * @return
     */
    List<TransactionResponse> getAllTransactions(Long entiteId);

    /**
     * Récupérer la liste paginée et trier par date
     *
     * @param page
     * @param size
     * @return
     */
    List<TransactionResponse> getLastTransactions(Long entiteId, int page, int size);

    /**
     * Renvoyer le nombre total des transactions
     *
     * @return
     */
    Integer getNbreTransactions(Long entiteId);

    /**
     * Vérifier si la devise a été utilisé
     *
     * @param id
     * @return
     */
    boolean checkUsedCurrency(Long id);

    /**
     * Vérifier si le compte bancaire a été utilisé
     *
     * @param id
     * @return
     */
    boolean checkUsedCompteBancaire(Long id);

    /**
     * Vérifier si le compte a été utilisé
     *
     * @param id
     * @return
     */
    boolean checkUsedCompte(Long id);

    /**
     * Récupérer la transaction en fonction de l'id
     *
     * @param id
     * @return
     */
    TransactionDto getTransaction(Long id);

    /**
     * Supprimer une transaction
     *
     * @param id
     */
    void delete(Long id);


    /**
     * Vérifier si le modepaiement a été utilisé
     *
     * @param id
     * @return
     */
    boolean checkUsedModePaiement(Long id);

    /**
     * Vérifier si la catégorie a été utilisé
     *
     * @param id
     * @return
     */
    boolean checkUsedCategorie(Long id);

    /**
     * Renvoyer les transactions liées à ce document
     *
     * @param documentId
     * @return
     */
    List<TransactionResponse> getTransactionByDocumentId(Long documentId);
}
