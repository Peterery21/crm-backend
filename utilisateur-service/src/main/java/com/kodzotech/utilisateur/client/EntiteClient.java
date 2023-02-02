package com.kodzotech.utilisateur.client;

import com.kodzotech.utilisateur.dto.EntiteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entite-service")
public interface EntiteClient {

    @GetMapping("/entites/{id}")
    public EntiteDto getEntite(@PathVariable Long id);

}
