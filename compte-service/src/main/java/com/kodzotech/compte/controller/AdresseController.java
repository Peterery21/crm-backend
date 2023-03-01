package com.kodzotech.compte.controller;

import com.kodzotech.compte.dto.AdresseDto;
import com.kodzotech.compte.service.AdresseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adresses")
@RequiredArgsConstructor
public class AdresseController {

    private final AdresseService adresseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@RequestBody AdresseDto adresseDto) {
        return adresseService.save(adresseDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long update(@PathVariable Long id,
                       @RequestBody AdresseDto adresseDto) {
        adresseDto.setId(id);
        return adresseService.save(adresseDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdresseDto getAdresse(@PathVariable Long id) {
        return adresseService.getAdresse(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        adresseService.delete(id);
    }

    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public List<AdresseDto> getAdressesById(@RequestParam List<Long> ids) {
        return adresseService.getAdressesById(ids);
    }

}
