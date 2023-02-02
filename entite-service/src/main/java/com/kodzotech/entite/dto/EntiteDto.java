package com.kodzotech.entite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntiteDto {

    private Long id;
    private String nom;
    private Integer niveau;
    private Long parentId;
    private Long societeId;
    private AdresseDto adresse;
    private Long deviseId;
}
