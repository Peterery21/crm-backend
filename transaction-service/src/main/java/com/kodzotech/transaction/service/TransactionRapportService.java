package com.kodzotech.transaction.service;

import com.kodzotech.transaction.dto.rapport.ResultatExploitationDto;
import com.kodzotech.transaction.dto.rapport.TransactionCategorieDto;
import com.kodzotech.transaction.dto.rapport.TransactionCompteBancaireDto;
import com.kodzotech.transaction.dto.rapport.TransactionJourDto;
import net.sf.jasperreports.engine.JRException;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;


public interface TransactionRapportService {

    /**
     * Renvoyer la somme des transactions par catégorie sur une période
     * avec le détail des transactions
     *
     * @param deviseId
     * @param sens
     * @param dateDebut
     * @param dateFin
     * @return
     */
    List<TransactionCategorieDto> getTransactionCategorieParPeriode(Long deviseId, String sens, LocalDate dateDebut, LocalDate dateFin);

    /**
     * Renvoyer la somme des transactions par catégorie et par date
     * avec le détail des transaction
     *
     * @param deviseId
     * @param sensType
     * @param dateDebut
     * @param dateFin
     * @return
     */
    List<TransactionJourDto> getTransactionCategorieParJour(Long deviseId, String sensType,
                                                            LocalDate dateDebut, LocalDate dateFin);

    /**
     * Renvoi la liste des transactions par compteBancaire
     * en fonction des paramètres
     *
     * @param compteBancaireIds
     * @param dateDebut
     * @param dateFin
     * @return
     */
    List<TransactionCompteBancaireDto> getTransactionParBanque(List<Long> compteBancaireIds,
                                                               LocalDate dateDebut, LocalDate dateFin);

    /**
     * Afficher le resultat (dépense / recette)
     *
     * @param deviseId
     * @param dateDebut
     * @param dateFin
     * @return
     */
    ResultatExploitationDto getResultatExploitation(Long deviseId, LocalDate dateDebut, LocalDate dateFin);

    byte[] printTransactionCategorieParJour(Long deviseId, String sensType,
                                            LocalDate dateDebut, LocalDate dateFin,
                                            HttpServletRequest request) throws JRException, FileNotFoundException;
}
