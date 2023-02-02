package com.kodzotech.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocieteDto implements Serializable {

    private Long id;
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
