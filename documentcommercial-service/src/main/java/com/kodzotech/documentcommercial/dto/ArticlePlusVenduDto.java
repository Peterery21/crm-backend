package com.kodzotech.documentcommercial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePlusVenduDto {
    private Long id;
    private String libelle;
    private Double valeur;
}
