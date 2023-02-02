package com.kodzotech.transaction.controller;

import com.kodzotech.transaction.dto.TaxeDto;
import com.kodzotech.transaction.service.TaxeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taxes")
@RequiredArgsConstructor
public class TaxeController {

    private final TaxeService taxeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody TaxeDto taxeDto) {
        taxeService.save(taxeDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @RequestBody TaxeDto taxeDto) {
        taxeDto.setId(id);
        taxeService.save(taxeDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaxeDto getTaxe(@PathVariable Long id) {
        return taxeService.getTaxe(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TaxeDto> getAllTaxe() {
        return taxeService.getAllTaxes();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        taxeService.delete(id);
    }

    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public List<TaxeDto> getTaxesById(@RequestParam List<Long> ids) {
        return taxeService.getTaxesById(ids);
    }

}
