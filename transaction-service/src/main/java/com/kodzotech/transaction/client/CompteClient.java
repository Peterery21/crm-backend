package com.kodzotech.transaction.client;

import com.kodzotech.transaction.dto.CompteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "compte-service")
public interface CompteClient {

    @GetMapping("/comptes/{id}")
    public CompteDto getCompte(@PathVariable Long id);

    @GetMapping("/comptes/byId")
    public List<CompteDto> getComptesById(@RequestParam List<Long> ids);
}
