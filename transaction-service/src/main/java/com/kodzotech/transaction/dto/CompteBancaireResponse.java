package com.kodzotech.transaction.dto;

import com.kodzotech.transaction.model.CompteBancaireType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CompteBancaireResponse implements Serializable {
    private Long id;
    private CompteBancaireType type;
    private String libelle;
    private DeviseDto devise;
}
