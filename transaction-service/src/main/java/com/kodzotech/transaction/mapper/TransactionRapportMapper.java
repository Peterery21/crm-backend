package com.kodzotech.transaction.mapper;

import com.kodzotech.transaction.service.CompteBancaireService;
import com.kodzotech.transaction.service.DeviseService;
import com.kodzotech.transaction.service.ModePaiementService;
import com.kodzotech.transaction.client.CompteClient;
import com.kodzotech.transaction.dto.CompteBancaireDto;
import com.kodzotech.transaction.dto.CompteDto;
import com.kodzotech.transaction.dto.DeviseDto;
import com.kodzotech.transaction.dto.ModePaiementDto;
import com.kodzotech.transaction.dto.rapport.TransactionDetailDto;
import com.kodzotech.transaction.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TransactionRapportMapper {

    private final DeviseService deviseService;
    private final CompteClient compteClient;
    private final CompteBancaireService compteBancaireService;
    private final ModePaiementService modePaiementService;


    public TransactionDetailDto entityToResponse(Transaction transaction,
                                                 List<DeviseDto> deviseDtoList,
                                                 List<CompteDto> compteDtoList,
                                                 List<ModePaiementDto> modePaiementDtoList,
                                                 List<CompteBancaireDto> compteBancaireDtoList) {
        TransactionDetailDto transactionDetailDto = new TransactionDetailDto();

        CompteDto compteDto = compteDtoList.stream()
                .filter(c -> c.getId().equals(transaction.getCompteId()))
                .findFirst().orElse(null);
        DeviseDto deviseDto = deviseDtoList.stream()
                .filter(c -> c.getId().equals(transaction.getDeviseId()))
                .findFirst().orElse(null);
        CompteBancaireDto compteBancaireDto = compteBancaireDtoList.stream()
                .filter(c -> c.getId().equals(transaction.getCompteBancaireId()))
                .findFirst().orElse(null);
        ModePaiementDto modePaiementDto = modePaiementDtoList.stream()
                .filter(c -> c.getId().equals(transaction.getModePaiementId()))
                .findFirst().orElse(null);

        transactionDetailDto.setDateTransaction(transaction.getDateTransaction());
        transactionDetailDto.setCategorie(transaction.getCategorieTransaction().getLibelle());
        transactionDetailDto.setReference(transaction.getReference());
        transactionDetailDto.setDescription(transaction.getDescription());
        //Montant à convertir plutard fonction de la devise
        transactionDetailDto.setMontant(transaction.getMontant());
        transactionDetailDto.setTaxe(transaction.getTaxe());
        transactionDetailDto.setSens(transaction.getSens());
        transactionDetailDto.setCompte(compteDto.getRaisonSociale());
        transactionDetailDto.setDevise(deviseDto.getLibelle());
        transactionDetailDto.setCodeDevise(deviseDto.getCode());
        transactionDetailDto.setCompteBancaire(compteBancaireDto.getLibelle());
        transactionDetailDto.setModePaiement(modePaiementDto.getLibelle());

        return transactionDetailDto;
    }

    public List<TransactionDetailDto> entityToResponse(List<Transaction> transactionList) {
        List<Long> deviseIdList = transactionList.stream().map(t -> t.getDeviseId()).collect(Collectors.toList());
        List<Long> compteIdList = transactionList.stream().map(t -> t.getCompteId()).collect(Collectors.toList());
        List<Long> compteBancaireIdList = transactionList.stream().map(t -> t.getCompteBancaireId()).collect(Collectors.toList());
        List<Long> modePaiementIdList = transactionList.stream().map(t -> t.getModePaiementId()).collect(Collectors.toList());

        List<DeviseDto> deviseDtoList = deviseService.getDevisesById(deviseIdList);
        List<CompteDto> compteDtoList = compteClient.getComptesById(compteIdList);
        List<ModePaiementDto> modePaiementDtoList = modePaiementService.getModePaiementsById(modePaiementIdList);
        List<CompteBancaireDto> compteBancaireDtoList = compteBancaireService.getCompteBancairesById(compteBancaireIdList);

        List<TransactionDetailDto> transactionResponseList = transactionList.stream()
                .flatMap(transaction -> Stream.of(entityToResponse(transaction, deviseDtoList, compteDtoList, modePaiementDtoList, compteBancaireDtoList)))
                .collect(Collectors.toList());
        return transactionResponseList;
    }
}
