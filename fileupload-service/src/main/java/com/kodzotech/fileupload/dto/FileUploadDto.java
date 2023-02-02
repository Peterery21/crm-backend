package com.kodzotech.fileupload.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadDto implements Serializable {
    private String filename;
    private String uploadFolder;
    private String subFolder;
    private MultipartFile file;
}
