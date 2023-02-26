package com.kodzotech.transaction.repository;

import com.kodzotech.transaction.dto.rapport.TransactionCategorieDto;
import com.kodzotech.transaction.dto.rapport.TransactionDetailDto;
import com.kodzotech.transaction.model.Devise;
import com.kodzotech.transaction.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean existsByReference(String reference);

    Optional<Transaction> findByReference(String reference);

    @Query("SELECT COALESCE(SUM(montant * sens), 0) FROM Transaction " +
            "WHERE  supprime=0 AND compteBancaireId=:compteBancaireId " +
            "AND deviseId=:deviseId")
    Double soldeCompteBancaire(Long compteBancaireId, Long deviseId);

    @Query("SELECT COALESCE(SUM(montant * sens), 0) FROM Transaction " +
            "WHERE  supprime=0 AND compteBancaireId=:compteBancaireId " +
            "AND dateTransaction <= :date")
    Double soldeCompteBancaire(Long compteBancaireId, LocalDate date);

    @Query("SELECT COUNT(*) FROM Transaction where supprime=0 AND  entiteId=:entiteId")
    Integer nbreTotalTransaction(Long entiteId);

    boolean existsByDeviseId(Long id);

    boolean existsByCompteBancaireId(Long id);

    boolean existsByCompteId(Long id);

    boolean existsByModePaiementId(Long id);

    @Query("SELECT COALESCE(SUM(montant * sens), 0) FROM Transaction where supprime=0 AND  deviseId=:deviseId")
    Double soldeTotal(Long deviseId);

    @Query("SELECT COALESCE(SUM(montant), 0) FROM Transaction WHERE supprime=0 AND  sens>0 and deviseId=:deviseId")
    Double totalRecette(Long deviseId);

    @Query("SELECT COALESCE(SUM(montant), 0) FROM Transaction WHERE supprime=0 AND  sens<0 and deviseId=:deviseId")
    Double totalDepense(Long deviseId);

    @Query("SELECT new com.kodzotech.transaction.dto.rapport.TransactionCategorieDto( " +
            "categorieTransaction.id as id, categorieTransaction.libelle as categorie, " +
            "SUM(COALESCE(montant, 0)) as valeur) " +
            "FROM Transaction " +
            "WHERE supprime=0 AND  sens=:sens AND deviseId=:deviseId " +
            "AND (dateTransaction<=:dateFin AND dateTransaction>=:dateDebut) " +
            "GROUP BY categorieTransaction.id, categorieTransaction.libelle " +
            "ORDER BY 3 DESC")
    List<TransactionCategorieDto> totalParCategorie(Long deviseId, int sens, LocalDate dateDebut, LocalDate dateFin);

    @Query("SELECT new com.kodzotech.transaction.dto.rapport.TransactionDetailDto( " +
            "dateTransaction, description, montant " +
            ") " +
            "FROM Transaction " +
            "WHERE supprime=0 AND  sens=:sens AND deviseId=:deviseId " +
            "AND categorieTransaction.id=:categorieId " +
            "AND (dateTransaction<=:dateFin AND dateTransaction>=:dateDebut) ")
    List<TransactionDetailDto> getDetailParCategorie(Long deviseId, int sens, Long categorieId,
                                                     LocalDate dateDebut, LocalDate dateFin);


    @Query("SELECT SUM(COALESCE(montant, 0)) as valeur " +
            "FROM Transaction " +
            "WHERE supprime=0 AND  sens=:sens AND deviseId=:deviseId " +
            "AND categorieTransaction.id=:categorieId " +
            "AND dateTransaction=:dateTransaction ")
    Double totalCategorieParMois(Long deviseId, int sens, Long categorieId,
                                 LocalDate dateTransaction);

    @Query("SELECT distinct dateTransaction FROM Transaction " +
            "WHERE supprime=0 AND  dateTransaction<=:dateFin AND dateTransaction>=:dateDebut " +
            "AND sens=:sens AND deviseId=:deviseId " +
            "ORDER BY dateTransaction")
    List<LocalDate> getDateTransactionBetweenDate(Long deviseId, int sens, LocalDate dateDebut, LocalDate dateFin);

    @Query("SELECT distinct categorieTransaction.id FROM Transaction " +
            "WHERE supprime=0 AND sens=:sens  AND deviseId=:deviseId " +
            "AND (dateTransaction<=:dateFin AND dateTransaction>=:dateDebut) ")
    List<Long> getCategorieTransactionUtilise(Long deviseId, int sens, LocalDate dateDebut, LocalDate dateFin);

    @Query("SELECT t " +
            "FROM Transaction t " +
            "WHERE supprime=0 " +
            "AND t.compteBancaireId in :compteBancaireIds " +
            "AND (dateTransaction<=:dateFin AND dateTransaction>=:dateDebut) " +
            "ORDER BY t.compteBancaireId, t.dateTransaction ASC")
    List<Transaction> getTransactionParCompteBancaire(List<Long> compteBancaireIds, LocalDate dateDebut, LocalDate dateFin);

    boolean existsByCategorieTransactionId(Long id);

    List<Transaction> findByDocumentIdAndSupprimeFalse(Long documentId);


    @Query("SELECT COALESCE(SUM(montant), 0) FROM Transaction " +
            "WHERE supprime=0 " +
            "AND documentId=:documentId")
    Double totalByDocument(Long documentId);

    List<Transaction> findAllBySupprimeFalseAndEntiteId(Long societeId);

    List<Transaction> findAllBySupprimeFalseAndEntiteId(Long societeId, Pageable pageable);

    @Query("SELECT DISTINCT t.deviseId FROM Transaction t")
    List<Long> findAllUsedDeviseByEntiteId();
}
