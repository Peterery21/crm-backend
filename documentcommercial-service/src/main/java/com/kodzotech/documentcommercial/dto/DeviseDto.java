package com.kodzotech.documentcommercial.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DeviseDto implements Serializable {
    private Long id;
    private String code;
    private String libelle;
    private int unite;
}
