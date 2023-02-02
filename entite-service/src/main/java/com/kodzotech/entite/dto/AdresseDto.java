package com.kodzotech.entite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdresseDto {
    private Long id;
    private String adresse;
    private String codePostal;
    private String ville;
    private String pays;
    private String telephone;
    private String portable;
    private String fax;
}
