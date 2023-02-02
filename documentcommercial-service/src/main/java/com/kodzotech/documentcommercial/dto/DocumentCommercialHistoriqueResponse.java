package com.kodzotech.documentcommercial.dto;

import com.kodzotech.documentcommercial.model.EtatDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentCommercialHistoriqueResponse {
    private Long id;
    private DocumentCommercialDto documentCommercial;
    private EtatDocument etat;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Instant date;
    private ResponsableDto utilisateur;
}
