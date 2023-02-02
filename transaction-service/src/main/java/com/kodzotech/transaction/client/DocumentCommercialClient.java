package com.kodzotech.transaction.client;

import com.kodzotech.transaction.dto.DocumentCommercialDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "documentcommercial-service")
public interface DocumentCommercialClient {

    @GetMapping("/documents/{id}")
    public DocumentCommercialDto getDocument(@PathVariable Long id);

    @GetMapping("/documents/byId")
    public List<DocumentCommercialDto> getDocumentsById(@RequestParam List<Long> ids);
}
