package com.kodzotech.documentcommercial.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarqueDto {
    private Long id;
    private String libelle;
}
