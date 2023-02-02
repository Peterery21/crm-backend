package com.kodzotech.entite.controller;

import com.kodzotech.entite.dto.EntiteChart;
import com.kodzotech.entite.dto.EntiteDto;
import com.kodzotech.entite.dto.EntiteResponse;
import com.kodzotech.entite.service.EntiteChartService;
import com.kodzotech.entite.service.EntiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entites")
@RequiredArgsConstructor
public class EntiteController {

    private final EntiteService entiteService;
    private final EntiteChartService entiteChartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody EntiteDto entiteDto) {
        entiteService.save(entiteDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @RequestBody EntiteDto entiteDto) {
        entiteDto.setId(id);
        entiteService.save(entiteDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntiteDto getEntite(@PathVariable Long id) {
        return entiteService.getEntite(id);
    }

    @GetMapping("/bySociete/{idSociete}")
    @ResponseStatus(HttpStatus.OK)
    public List<EntiteResponse> getAllEntite(@PathVariable Long idSociete) {
        return entiteChartService.getAllEntitesBySociete(idSociete);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        entiteService.delete(id);
    }

    @GetMapping("/chart/{idSociete}")
    @ResponseStatus(HttpStatus.OK)
    public List<EntiteChart> getEntiteChart(@PathVariable Long idSociete) {
        return entiteChartService.getEntiteChart(idSociete);
    }

}
