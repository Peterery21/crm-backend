package com.kodzotech.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdresseDto implements Serializable {
    private Long id;
    private String adresse;
    private String codePostal;
    private String ville;
    private String pays;
    private String telephone;
    private String portable;
    private String fax;
}
