package com.kodzotech.transaction.client;

import com.kodzotech.transaction.dto.SocieteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entite-service")
public interface EntiteClient {

    @GetMapping("/societes/{id}")
    public SocieteDto getSociete(@PathVariable Long id);
}
