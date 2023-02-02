package com.kodzotech.transaction.dto.rapport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionJourDto implements Serializable {

    private Long id;
    private String categorie;
    private Double valeur;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateTransaction;
    private List<TransactionDetailDto> transactions = new LinkedList<>();

    public TransactionJourDto(Long id, String categorie, Double valeur, LocalDate dateTransaction) {
        this.id = id;
        this.categorie = categorie;
        this.valeur = valeur;
        this.dateTransaction = dateTransaction;
    }
}