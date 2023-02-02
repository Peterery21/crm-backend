package com.kodzotech.utilisateur.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocieteDto implements Serializable {

    private Long id;
    private String nom;
}
