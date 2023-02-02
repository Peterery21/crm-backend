package com.kodzotech.documentcommercial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsableDto implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
}
