package com.kodzotech.transaction.utils;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class Countries {
    private String code;
    private String pays;
    private String libelle;
    private String symbole;
    private int unite;
}
