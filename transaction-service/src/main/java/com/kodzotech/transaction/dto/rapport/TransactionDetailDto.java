package com.kodzotech.transaction.dto.rapport;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TransactionDetailDto implements Serializable {
    private Double montant;
    private String description;
    private String compte;
    private String compteBancaire;
    private String categorie;
    private String reference;
    private String codeDevise;
    private String devise;
    private String modePaiement;
    private int sens;
    private Double taxe;
    private Double balance;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateTransaction;

    public TransactionDetailDto(LocalDate dateTransaction, String description, Double montant) {
        this.dateTransaction = dateTransaction;
        this.description = description;
        this.montant = montant;
    }
}