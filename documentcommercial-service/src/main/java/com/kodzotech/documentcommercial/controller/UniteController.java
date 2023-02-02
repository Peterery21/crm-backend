package com.kodzotech.documentcommercial.controller;


import com.kodzotech.documentcommercial.dto.UniteDto;
import com.kodzotech.documentcommercial.service.UniteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unites")
@RequiredArgsConstructor
@Slf4j
public class UniteController {

    private final UniteService uniteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody UniteDto uniteDto) {
        uniteService.save(uniteDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @RequestBody UniteDto uniteDto) {
        uniteDto.setId(id);
        uniteService.save(uniteDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UniteDto getUnite(@PathVariable Long id) {
        return uniteService.getUnite(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UniteDto> getAllUnite() {
        return uniteService.getAllUnites();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        uniteService.delete(id);
    }

}
