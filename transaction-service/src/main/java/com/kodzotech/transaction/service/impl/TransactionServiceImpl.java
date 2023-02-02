package com.kodzotech.transaction.service.impl;

import com.kodzotech.transaction.client.CompteClient;
import com.kodzotech.transaction.exception.TransactionException;
import com.kodzotech.transaction.mapper.TransactionMapper;
import com.kodzotech.transaction.service.CompteBancaireService;
import com.kodzotech.transaction.service.DeviseService;
import com.kodzotech.transaction.service.TransactionService;
import com.kodzotech.transaction.service.TransactionSoldeService;
import com.kodzotech.transaction.dto.TransactionDto;
import com.kodzotech.transaction.dto.TransactionResponse;
import com.kodzotech.transaction.model.SensType;
import com.kodzotech.transaction.model.Transaction;
import com.kodzotech.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final DeviseService deviseService;
    private final CompteBancaireService compteBancaireService;
    private final CompteClient compteClient;
    private final TransactionSoldeService transactionSoldeService;

    @Transactional
    @Override
    public void save(Transaction transaction) {
        verifierTransaction(transaction);
        if (transaction.getReference() == null || transaction.getReference().trim().isEmpty()) {
            //Génération de numero de référence si inexistant
        }
        transactionRepository.save(transaction);
    }

    @Transactional
    @Override
    public void update(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.dtoToEntity(transactionDto);
        verifierTransaction(transaction);
        Transaction transactionOriginal = transactionRepository
                .findById(transactionDto.getId())
                .orElseThrow(() -> new TransactionException("erreur.transaction.id.non.trouve"));
        transactionOriginal = transactionMapper.dtoToEntity(transactionOriginal, transaction);
        transactionRepository.save(transactionOriginal);
    }

    @Override
    public void verifierTransaction(Transaction transaction) {
        if (transaction.getDateTransaction() == null || transaction.getDateTransaction().isAfter(LocalDate.now())) {
            throw new TransactionException("erreur.transaction.date.transaction.incorrect");
        }
        if (transaction.getMontant() == 0D) {
            throw new TransactionException("erreur.transaction.montant.non.valide");
        }
        if (transaction.getCategorieTransaction() == null) {
            throw new TransactionException("erreur.transaction.categorie.transaction.non.valide");
        }
        //Vérifier CompteBancaire
        if (transaction.getCompteBancaireId() == null) {
            throw new TransactionException("erreur.transaction.comptebancaire.non.valide");
        } else {
            compteBancaireService.getCompteBancaire(transaction.getCompteBancaireId());
        }
        //Vérifier Compte
        if (transaction.getCompteId() == null) {
            throw new TransactionException("erreur.transaction.compte.non.valide");
        } else {
            compteClient.getCompte(transaction.getCompteId());
        }
        //Vérifier Devise
        if (transaction.getDeviseId() == null) {
            throw new TransactionException("erreur.transaction.devise.non.valide");
        } else {
            deviseService.getDevise(transaction.getDeviseId());
        }
        //Update mode
        if (transaction.getReference() != null && !transaction.getReference().trim().isEmpty()) {
            if (transaction.getId() != null) {
                Transaction transact = transactionRepository.findByReference(transaction.getReference()).orElse(null);
                if (transact != null) {
                    if (transact.getId().longValue() != transaction.getId().longValue()) {
                        throw new TransactionException("erreur.transaction.reference.doublon");
                    }
                } else {
                    if (transactionRepository.existsByReference(transaction.getReference()))
                        throw new TransactionException("erreur.transaction.reference.doublon");
                }
            }
        }
    }

    @Override
    public void annulerTransaction(String reference) {

    }

    @Override
    public void effectuerDepense(TransactionDto transactionDto) {
        Validate.notNull(transactionDto);
        Transaction transaction = transactionMapper.dtoToEntity(transactionDto);
        transaction.setSens(SensType.DEPENSE);
        //Vérification de solde
        Double solde = transactionSoldeService.getSoldeCompteBancaireParDate(
                transaction.getCompteBancaireId(),
                transaction.getDateTransaction());
        if (solde == null || solde <= 0) {
            throw new TransactionException("erreur.transaction.solde.compte.insuffisant");
        }
        save(transaction);
    }

    @Override
    public void effectuerRecette(TransactionDto transactionDto) {
        Validate.notNull(transactionDto);
        Transaction transaction = transactionMapper.dtoToEntity(transactionDto);
        transaction.setSens(SensType.RECETTE);
        save(transaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactions(Long entiteId) {
        List<Transaction> transactionList = transactionRepository
                .findAllBySupprimeFalseAndEntiteId(entiteId);
        return transactionMapper.entityToResponse(transactionList);
    }

    @Override
    public List<TransactionResponse> getLastTransactions(Long entiteId, int page, int size) {
        Pageable sortedByDateDesc =
                PageRequest.of(page, size, Sort.by("dateTransaction").descending());
        List<Transaction> transactionList = transactionRepository
                .findAllBySupprimeFalseAndEntiteId(entiteId, sortedByDateDesc);
        return transactionMapper.entityToResponse(transactionList);
    }

    @Override
    public Integer getNbreTransactions(Long entiteId) {
        return transactionRepository.nbreTotalTransaction(entiteId);
    }

    @Override
    public boolean checkUsedCurrency(Long id) {
        return transactionRepository.existsByDeviseId(id);
    }

    @Override
    public boolean checkUsedCompteBancaire(Long id) {
        return transactionRepository.existsByCompteBancaireId(id);
    }

    @Override
    public boolean checkUsedCompte(Long id) {
        return transactionRepository.existsByCompteId(id);
    }

    @Override
    public TransactionDto getTransaction(Long id) {
        return transactionMapper.entityToDto(transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionException("erreur.transaction.id.non.trouve")));
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = transactionRepository
                .findById(id)
                .orElseThrow(() -> new TransactionException("erreur.transaction.id.non.trouve"));
        transaction.setSupprime(true);
        transactionRepository.save(transaction);
    }

    @Override
    public boolean checkUsedModePaiement(Long id) {
        return transactionRepository.existsByModePaiementId(id);
    }

    @Override
    public boolean checkUsedCategorie(Long id) {
        return transactionRepository.existsByCategorieTransactionId(id);
    }

    @Override
    public List<TransactionResponse> getTransactionByDocumentId(Long documentId) {
        return transactionMapper.entityToResponse(transactionRepository.findByDocumentIdAndSupprimeFalse(documentId));
    }
}
