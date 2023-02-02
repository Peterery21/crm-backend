package com.kodzotech.compte.dto;

import com.kodzotech.compte.model.CompteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompteDto {
    private Long id;
    private CompteType type;
    private String raisonSociale;
    private Long categorieCompteId;
    private Long tailleId;
    private Long secteurActiviteId;
    private Long responsableId;
    private AdresseDto adresse;
    private AdresseDto adresseLivraison;
    private Boolean adresseIdentique;
    private String email;
    private String site;
    private String registreCommerce;
    private Boolean estSociete;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateCreation;

    private Long utilisateurId;
    private Long societeId;
    private Long entiteId;
}
