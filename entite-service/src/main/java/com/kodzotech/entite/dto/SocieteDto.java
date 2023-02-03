package com.kodzotech.entite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocieteDto implements Serializable {

    private Long id;
    @NotNull
    private String raisonSociale;
    private Long categorieCompteId;
    private Long tailleId;
    private Long secteurActiviteId;
    private AdresseDto adresse;
    private String email;
    private String site;
    private String registreCommerce;
    private String logo;
    private String logoUrl;
}
