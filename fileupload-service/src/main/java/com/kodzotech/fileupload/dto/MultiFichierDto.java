package com.kodzotech.fileupload.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MultiFichierDto {
    private Long id;
    private String categorie;
    private Long idObjet;
    private String subFolder;
    private List<MultipartFile> file;
}
