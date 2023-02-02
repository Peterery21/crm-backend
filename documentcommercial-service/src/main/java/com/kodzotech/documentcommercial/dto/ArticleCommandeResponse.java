package com.kodzotech.documentcommercial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleCommandeResponse {

    private Long id;
    private Long articleId;
    private String reference;
    private String designation;
    private String description;
    private Double prixRevient;
    private Double prixUnitaire;
    private String unite;
    private Integer quantite;
    private TaxeCommandeDto taxe;
}
