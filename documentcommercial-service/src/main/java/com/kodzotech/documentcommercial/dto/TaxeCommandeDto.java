package com.kodzotech.documentcommercial.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaxeCommandeDto {
    private Long id;
    private Long taxeId;
    private String libelle;
    private Double valeur;
    private Double total;
    private Boolean estTaxeCompose;
    private List<TaxeCommandeDto> taxeFils;
}
