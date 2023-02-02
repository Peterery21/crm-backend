package com.kodzotech.compte.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategorieCompteDto {
    private Long id;
    private String libelle;
}
