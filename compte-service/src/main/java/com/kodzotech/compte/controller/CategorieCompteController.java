package com.kodzotech.compte.controller;

import com.kodzotech.compte.dto.CategorieCompteDto;
import com.kodzotech.compte.service.CategorieCompteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategorieCompteController {

    private final CategorieCompteService categorieCompteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody CategorieCompteDto categorieCompteDto) {
        categorieCompteService.save(categorieCompteDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @RequestBody CategorieCompteDto categorieCompteDto) {
        categorieCompteDto.setId(id);
        categorieCompteService.save(categorieCompteDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategorieCompteDto getCategorieCompte(@PathVariable Long id) {
        return categorieCompteService.getCategorieCompte(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CategorieCompteDto> getAllCategorieCompte() {
        return categorieCompteService.getAllCategoriesCompte();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        categorieCompteService.delete(id);
    }

}
