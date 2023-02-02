package com.kodzotech.compte.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TailleDto {
    private Long id;
    private String libelle;
}
