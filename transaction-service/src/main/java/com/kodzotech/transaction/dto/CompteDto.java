package com.kodzotech.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteDto implements Serializable {

    private Long id;
    private String raisonSociale;
}
