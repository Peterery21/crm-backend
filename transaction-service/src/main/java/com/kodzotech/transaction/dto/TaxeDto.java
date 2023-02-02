package com.kodzotech.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaxeDto {
    private Long id;
    private String libelle;
    private Double valeur;
    private Boolean estTaxeCompose;
    private List<Long> idtaxeFils;
    private List<TaxeDto> taxeFils;
}
