package com.kodzotech.compte.controller;

import com.kodzotech.compte.dto.SecteurActiviteDto;
import com.kodzotech.compte.service.SecteurActiviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secteuractivites")
@RequiredArgsConstructor
public class SecteurActiviteController {

    private final SecteurActiviteService secteurActiviteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody SecteurActiviteDto secteurActiviteDto) {
        secteurActiviteService.save(secteurActiviteDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @RequestBody SecteurActiviteDto secteurActiviteDto) {
        secteurActiviteDto.setId(id);
        secteurActiviteService.save(secteurActiviteDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SecteurActiviteDto getSecteurActivite(@PathVariable Long id) {
        return secteurActiviteService.getSecteurActivite(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<SecteurActiviteDto> getAllSecteurActivite() {
        return secteurActiviteService.getAllCategoriesCompte();
    }

    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public List<SecteurActiviteDto> getSecteurActivitesById(@RequestParam List<Long> ids) {
        return secteurActiviteService.getSecteurActivitesById(ids);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        secteurActiviteService.delete(id);
    }

}
