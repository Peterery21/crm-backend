package com.kodzotech.transaction.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviseDto {
    private Long id;
    private String code;
    private String pays;
    private String libelle;
    private String symbole;
    private int unite;
}
