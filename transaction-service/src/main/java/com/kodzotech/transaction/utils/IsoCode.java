package com.kodzotech.transaction.utils;

import lombok.Data;

@Data
public class IsoCode {
    private int id;
    private String name;
    private String isoAlpha2;
    private String isoAlpha3;
    private double isoNumeric;
    private Currency currency;
}