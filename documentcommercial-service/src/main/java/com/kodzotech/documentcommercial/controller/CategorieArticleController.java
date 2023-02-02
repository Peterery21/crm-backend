package com.kodzotech.documentcommercial.controller;

import com.kodzotech.documentcommercial.dto.CategorieArticleDto;
import com.kodzotech.documentcommercial.dto.CategorieArticleResponse;
import com.kodzotech.documentcommercial.service.CategorieArticleChartService;
import com.kodzotech.documentcommercial.service.CategorieArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategorieArticleController {

    private final CategorieArticleService categorieArticleService;
    private final CategorieArticleChartService categorieArticleChartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody CategorieArticleDto categorieArticleDto) {
        categorieArticleService.save(categorieArticleDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @RequestBody CategorieArticleDto categorieArticleDto) {
        categorieArticleDto.setId(id);
        categorieArticleService.save(categorieArticleDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategorieArticleDto getCategorieArticle(@PathVariable Long id) {
        return categorieArticleService.getCategorieArticle(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CategorieArticleResponse> getAllCategorieArticle() {
        return categorieArticleChartService.getAllCategorieArticlesBySociete();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        categorieArticleService.delete(id);
    }

}
