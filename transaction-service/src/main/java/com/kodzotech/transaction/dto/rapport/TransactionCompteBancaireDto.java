package com.kodzotech.transaction.dto.rapport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCompteBancaireDto implements Serializable {

    private Long id;
    private String compteBancaire;
    private String devise;
    private Double valeur;
    private List<TransactionDetailDto> transactions = new LinkedList<>();
}