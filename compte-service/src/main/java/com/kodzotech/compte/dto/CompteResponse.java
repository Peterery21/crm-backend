package com.kodzotech.compte.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class CompteResponse {
    private Long id;
    private String type;
    private String raisonSociale;
    private CategorieCompteDto categorie;
    private SecteurActiviteDto secteurActivite;
    private TailleDto taille;
    private AdresseDto adresse;
    private AdresseDto adresseLivraison;
    private Boolean adresseIdentique;
    private ResponsableDto responsable;
    private String email;
    private String site;
    private String registreCommerce;
    private Boolean estSociete;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateCreation;

    private Long utilisateurId;
    private Long societeId;
    private Long entiteId;
}
