package com.kodzotech.entite.client;

import com.kodzotech.entite.dto.AdresseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "compte-service")
public interface AdresseClient {

    @GetMapping("/adresses/byId")
    public List<AdresseDto> getAdressesById(@RequestParam List<Long> ids);

    @GetMapping("/adresses/{id}")
    public AdresseDto getAdresse(@PathVariable Long id);

    @PostMapping("/adresses")
    public Long save(@RequestBody AdresseDto adresseDto);

    @PutMapping("/adresses/{id}")
    public Long update(@PathVariable Long id, @RequestBody AdresseDto adresseDto);

    @DeleteMapping("/adresses/{id}")
    public void delete(@PathVariable Long id);
}
