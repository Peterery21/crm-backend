package com.kodzotech.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class StatDto implements Serializable {
    List<DeviseValeurDto> depenses;
    List<DeviseValeurDto> recettes;
    List<DeviseValeurDto> soldes;
}
