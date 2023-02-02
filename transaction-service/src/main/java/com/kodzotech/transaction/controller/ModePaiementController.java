package com.kodzotech.transaction.controller;

import com.kodzotech.transaction.dto.ModePaiementDto;
import com.kodzotech.transaction.service.ModePaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modepaiements")
@RequiredArgsConstructor
public class ModePaiementController {

    public final ModePaiementService modePaiementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody ModePaiementDto modePaiementDto) {
        modePaiementService.save(modePaiementDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @RequestBody ModePaiementDto modePaiementDto) {
        modePaiementDto.setId(id);
        modePaiementService.save(modePaiementDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ModePaiementDto getModePaiement(@PathVariable Long id) {
        return modePaiementService.getModePaiement(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ModePaiementDto> getAllModePaiement() {
        return modePaiementService.getAllModePaiement();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        modePaiementService.delete(id);
    }

    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public List<ModePaiementDto> getDevisesById(@RequestParam List<Long> ids) {
        return modePaiementService.getModePaiementsById(ids);
    }
}
