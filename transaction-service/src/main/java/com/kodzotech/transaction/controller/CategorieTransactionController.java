package com.kodzotech.transaction.controller;

import com.kodzotech.transaction.dto.CategorieTransactionDto;
import com.kodzotech.transaction.model.SensType;
import com.kodzotech.transaction.service.CategorieTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategorieTransactionController {

    private final CategorieTransactionService categorieTransactionService;

    @PostMapping("/depense")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createCategorieDepense(@Valid @RequestBody CategorieTransactionDto categorieTransactionDto){
        categorieTransactionService.saveCategorieDepense(categorieTransactionDto);
    }

    @PostMapping("/recette")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createCategorieRecette(@Valid @RequestBody CategorieTransactionDto categorieTransactionDto){
        categorieTransactionService.saveCategorieRecette(categorieTransactionDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateCategorie(@PathVariable Long id, @Valid @RequestBody CategorieTransactionDto categorieTransactionDto){
        categorieTransactionDto.setId(id);
        categorieTransactionService.update(categorieTransactionDto);
    }

    @GetMapping("/depense")
    @ResponseStatus(code = HttpStatus.OK)
    public List<CategorieTransactionDto> getAllDepenseCategories(){
        return categorieTransactionService.getAllCategoriesByType(SensType.CATEG_DEPENSE);
    }

    @GetMapping("/recette")
    @ResponseStatus(code = HttpStatus.OK)
    public List<CategorieTransactionDto> getAllRecetteCategories(){
        return categorieTransactionService.getAllCategoriesByType(SensType.CATEG_RECETTE);
    }


    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CategorieTransactionDto getCategorie(@PathVariable Long id){
        return categorieTransactionService.getCategorie(id);
    }

    @GetMapping("/code/{code}")
    @ResponseStatus(code = HttpStatus.OK)
    public CategorieTransactionDto getCategorie(@PathVariable String code){
        return categorieTransactionService.getCategorieByCode(code);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void supprimer(@PathVariable Long id){
        categorieTransactionService.supprimer(id);
    }
}
