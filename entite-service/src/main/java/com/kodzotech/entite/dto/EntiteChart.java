package com.kodzotech.entite.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EntiteChart {
    @EqualsAndHashCode.Include
    Long id;
    String name;
    String cssClass;
    String image;
    String title;
    List<EntiteChart> childs;

    public EntiteChart(Long id) {
        this.id = id;
    }
}
