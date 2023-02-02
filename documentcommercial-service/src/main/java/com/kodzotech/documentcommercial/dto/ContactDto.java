package com.kodzotech.documentcommercial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
    private String fonction;
    private String telephone;
    private String email;
    private String portable;
    private Long responsableId;
    private Long compteId;
}

