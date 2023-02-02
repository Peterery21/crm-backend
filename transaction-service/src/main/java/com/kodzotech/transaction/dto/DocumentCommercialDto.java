package com.kodzotech.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentCommercialDto {
    private Long id;
    private String categorie;
    private String type;
    private String etat;
    private String reference;
    private String objet;
    private Long compteId;
    private Long contactId;
    private Long responsableId;
    private Long deviseId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEmission;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateExpiration;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEcheance;
    private Double taxe;
    private String introduction;
    private String conditionReglement;
    private String note;

    @JsonIgnore
    private Long utilisateurId;
    @JsonIgnore
    private Long updateUtilisateurId;
    @JsonIgnore
    private Long societeId;
    @JsonIgnore
    private Long entiteId;
}
