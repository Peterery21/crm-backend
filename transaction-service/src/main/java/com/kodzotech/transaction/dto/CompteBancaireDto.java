package com.kodzotech.transaction.dto;

import com.kodzotech.transaction.model.CompteBancaireType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompteBancaireDto implements Serializable {

    private Long id;
    private CompteBancaireType type;
    private String libelle;
    private Long deviseId;
}
