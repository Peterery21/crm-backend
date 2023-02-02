package com.kodzotech.documentcommercial.client;

import com.kodzotech.documentcommercial.dto.AdresseDto;
import com.kodzotech.documentcommercial.dto.ContactDto;
import com.kodzotech.documentcommercial.dto.CompteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "compte-service")
public interface CompteClient {

    @GetMapping("/comptes/{id}")
    public CompteDto getCompte(@PathVariable Long id);

    @GetMapping("/comptes/byId")
    public List<CompteDto> getComptesById(@RequestParam List<Long> ids);

    @GetMapping("/contacts/{id}")
    public ContactDto getContact(@PathVariable Long id);

    @GetMapping("/contacts/byId")
    public List<ContactDto> getContactsById(@RequestParam List<Long> ids);

    @GetMapping("/adresses/byId")
    public List<AdresseDto> getAdressesById(@RequestParam List<Long> ids);

    @GetMapping("/adresses/{id}")
    public AdresseDto getAdresse(@PathVariable Long id);

    @PostMapping("/adresses")
    public Long saveAdresse(@RequestBody AdresseDto adresseDto);

    @PutMapping("/adresses/{id}")
    public Long updateAdresse(@PathVariable Long id, @RequestBody AdresseDto adresseDto);

    @DeleteMapping("/adresses/{id}")
    public void deleteAdresse(@PathVariable Long id);
}
