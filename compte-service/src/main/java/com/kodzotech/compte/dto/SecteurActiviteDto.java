package com.kodzotech.compte.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecteurActiviteDto implements Serializable {
    private Long id;
    private String libelle;
}
