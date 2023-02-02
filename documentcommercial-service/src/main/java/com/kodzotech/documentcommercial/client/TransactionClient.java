package com.kodzotech.documentcommercial.client;

import com.kodzotech.documentcommercial.dto.TaxeCommandeDto;
import com.kodzotech.documentcommercial.dto.DeviseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "transaction-service")
public interface TransactionClient {

    @GetMapping("/soldes/document/{documentId}")
    public Double getTotalByDocument(@PathVariable Long documentId);

    @GetMapping("/devises/{id}")
    public DeviseDto getDevise(@PathVariable Long id);

    @GetMapping("/devises")
    public List<DeviseDto> getAllDevise();

    @GetMapping("/devises/byId")
    public List<DeviseDto> getDevisesById(@RequestParam List<Long> ids);

    @GetMapping("/taxes/byId")
    public List<TaxeCommandeDto> getTaxesById(@RequestParam List<Long> ids);


}
