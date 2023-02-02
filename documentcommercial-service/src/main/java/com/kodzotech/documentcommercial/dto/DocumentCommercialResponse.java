package com.kodzotech.documentcommercial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentCommercialResponse {
    private Long id;
    private String categorie;
    private String type;
    private String etat;
    private String reference;
    private String numero;
    private String objet;
    private CompteDto compte;
    private ContactDto contact;
    private ResponsableDto responsable;
    private AdresseDto adresse;
    private AdresseDto adresseLivraison;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEmission;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateExpiration;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEcheance;
    List<TaxeCommandeDto> taxes;
    List<ArticleCommandeResponse> articles;
    private String introduction;
    private String conditionReglement;
    private String note;
    private Double montantHT;
    private Double montantTTC;
    private DeviseDto devise;
    private Long documentInitialId;
    private boolean actif;

    private Double montantRegle;
    private Double montantRestant;

    private Long societeId;
}
