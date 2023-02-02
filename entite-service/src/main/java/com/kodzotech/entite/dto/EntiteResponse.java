package com.kodzotech.entite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntiteResponse implements Serializable {

    private Long id;
    private String nom;
    private Integer niveau;
    private EntiteDto parent;
    private SocieteDto societe;
    private AdresseDto adresse;
}
