package com.kodzotech.transaction.client;

import com.kodzotech.transaction.dto.ModePaiementDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "modepaiement-service")
public interface ModePaiementClient {

    @GetMapping("/modepaiements/{id}")
    public ModePaiementDto getModePaiement(@PathVariable Long id);

    @GetMapping("/modepaiements/byId")
    public List<ModePaiementDto> getModePaiementsById(@RequestParam List<Long> ids);
}
