package com.kodzotech.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto implements Serializable {
    private Long id;
    @NotNull
    private Double montant;
    private String description;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateTransaction;
    private Long categorieId;
    private String reference;
    private Long compteBancaireId;
    private Long compteId;
    private Long deviseId;
    private Long modePaiementId;
    private Double taxe;

    private Long documentId;

    @JsonIgnore
    private Long utilisateurId;
    @JsonIgnore
    private Long societeId;
    @JsonIgnore
    private Long entiteId;
}
