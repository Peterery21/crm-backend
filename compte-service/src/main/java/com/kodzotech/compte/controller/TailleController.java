package com.kodzotech.compte.controller;

import com.kodzotech.compte.dto.TailleDto;
import com.kodzotech.compte.service.TailleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tailles")
@RequiredArgsConstructor
public class TailleController {

    private final TailleService tailleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody TailleDto tailleDto) {
        tailleService.save(tailleDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @RequestBody TailleDto tailleDto) {
        tailleDto.setId(id);
        tailleService.save(tailleDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TailleDto getTaille(@PathVariable Long id) {
        return tailleService.getTaille(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TailleDto> getAllTaille() {
        return tailleService.getAllTailles();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        tailleService.delete(id);
    }

}
