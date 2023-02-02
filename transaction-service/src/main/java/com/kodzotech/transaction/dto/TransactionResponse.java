package com.kodzotech.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse implements Serializable {
    private Long id;
    private CategorieTransactionDto categorieTransaction;
    private CompteBancaireDto compteBancaire;
    private CompteDto compte;
    private DeviseDto devise;
    private ModePaiementDto modePaiement;
    private Double montant;
    private String description;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTransaction;
    private String reference;
    private Double taxe;
    private int sens;
    private DocumentCommercialDto documentCommercial;
}
