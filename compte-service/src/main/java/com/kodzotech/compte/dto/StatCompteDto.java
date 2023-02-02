package com.kodzotech.compte.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class StatCompteDto implements Serializable {
    Long clients;
    Long fournisseurs;
    Long prospects;
}
