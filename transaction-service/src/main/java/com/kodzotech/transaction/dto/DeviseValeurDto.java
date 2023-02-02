package com.kodzotech.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class DeviseValeurDto implements Serializable {
    String devise;
    Double valeur;
}