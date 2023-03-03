package com.kodzotech.entite.controller;

import com.kodzotech.entite.dto.SocieteDto;
import com.kodzotech.entite.service.SocieteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/societes")
@RequiredArgsConstructor
public class SocieteController {

    private final SocieteService societeService;

    @Value("${api.gateway.url}")
    private String apiGatewayUrl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody SocieteDto societeDto) {
        societeService.save(societeDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @RequestBody SocieteDto societeDto) {
        societeDto.setId(id);
        societeService.save(societeDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SocieteDto getSociete(@PathVariable Long id, HttpServletRequest request) {
        SocieteDto societeDto = societeService.getSociete(id);
        String baseUrl = request.getHeader("origin");
        if (societeDto.getLogo() != null) {
            //TODO intérroger, fileupload-service pour récupérer le chemin complet
            societeDto.setLogoUrl(baseUrl + "/api/fileupload-service/content/images/" + societeDto.getLogo());
        }
        return societeDto;
    }
}
