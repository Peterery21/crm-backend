package com.kodzotech.entite.client;

import com.kodzotech.entite.dto.FileUploadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "fileupload-service")
public interface FileUploadClient {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveFile(@ModelAttribute FileUploadDto fileUploadDto);

}
