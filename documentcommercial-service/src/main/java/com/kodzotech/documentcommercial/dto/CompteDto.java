package com.kodzotech.documentcommercial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompteDto implements Serializable {
    private Long id;
    private String raisonSociale;
    private AdresseDto adresse;
    private AdresseDto adresseLivraison;
    private Boolean adresseIdentique;
    private ResponsableDto responsable;
    private String email;
    private String site;
    private String registreCommerce;
    private String telephone;
    private String portable;
    private String fax;
    private Boolean estSociete;
}