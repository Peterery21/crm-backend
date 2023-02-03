package com.kodzotech.entite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntiteDto {

    private Long id;
    @NotNull
    private String nom;
    private Integer niveau;
    private Long parentId;
    @NotNull
    private Long societeId;
    private AdresseDto adresse;
    private Long deviseId;
}
