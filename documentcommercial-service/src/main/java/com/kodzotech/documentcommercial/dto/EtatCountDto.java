package com.kodzotech.documentcommercial.dto;

import com.kodzotech.documentcommercial.model.CategorieDocument;
import com.kodzotech.documentcommercial.model.EtatDocument;
import com.kodzotech.documentcommercial.model.TypeDocument;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class EtatCountDto {

    private CategorieDocument categorie;
    private TypeDocument type;
    private EtatDocument etat;
    private Long total;

    public EtatCountDto(CategorieDocument categorie, TypeDocument type,
                        EtatDocument etat, Long total) {
        this.categorie = categorie;
        this.type = type;
        this.etat = etat;
        this.total = total;
    }

    public EtatCountDto(CategorieDocument categorie, TypeDocument type,
                        Long total) {
        this.categorie = categorie;
        this.type = type;
        this.total = total;
    }

}
