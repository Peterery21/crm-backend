package com.kodzotech.documentcommercial.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategorieArticleResponse {
    private Long id;
    private String libelle;
    private Integer niveau;
    private CategorieArticleDto parent;
}
