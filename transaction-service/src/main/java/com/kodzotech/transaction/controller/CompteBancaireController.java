package com.kodzotech.transaction.controller;

import com.kodzotech.transaction.dto.CompteBancaireDto;
import com.kodzotech.transaction.dto.CompteBancaireResponse;
import com.kodzotech.transaction.model.CompteBancaireType;
import com.kodzotech.transaction.service.CompteBancaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comptebancaires")
@RequiredArgsConstructor
public class CompteBancaireController {

    private final CompteBancaireService compteBancaireService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CompteBancaireDto compteBancaireDto) {
        compteBancaireService.save(compteBancaireDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void create(@PathVariable Long id, @RequestBody CompteBancaireDto compteBancaireDto) {
        compteBancaireDto.setId(id);
        compteBancaireService.save(compteBancaireDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompteBancaireDto getCompteBancaire(@PathVariable Long id) {
        return compteBancaireService.getCompteBancaire(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CompteBancaireResponse> getAllCompteBancaire() {
        return compteBancaireService.getAllCompteBancaire();
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        compteBancaireService.supprimer(id);
    }

    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public List<CompteBancaireDto> getComptesById(@RequestParam List<Long> ids) {
        return compteBancaireService.getCompteBancairesById(ids);
    }

    @GetMapping("/type/{type}")
    @ResponseStatus(HttpStatus.OK)
    public List<CompteBancaireDto> getCompteBancaire(@PathVariable CompteBancaireType type) {
        return compteBancaireService.getCompteBancaireParType(type);
    }
}
