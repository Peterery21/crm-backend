package com.kodzotech.transaction.dto.rapport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCategorieDto implements Serializable {

    private Long id;
    private String categorie;
    private Double valeur;
    private List<TransactionDetailDto> transactions = new LinkedList<>();

    public TransactionCategorieDto(Long id, String categorie, Double valeur) {
        this.id = id;
        this.categorie = categorie;
        this.valeur = valeur;
    }
}