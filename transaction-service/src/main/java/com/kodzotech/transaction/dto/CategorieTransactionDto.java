package com.kodzotech.transaction.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
public class CategorieTransactionDto implements Serializable {

    private Long id;
    @NotBlank
    private String libelle;
    private String code;
}
