package com.kodzotech.documentcommercial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FichierDto {
    private Long id;
    private String categorie;
    private String fileUrl;
    private String filename;
    private String uploadFolder;
    private String subFolder;
    private MultipartFile file;
    private Long idObjet;
}
