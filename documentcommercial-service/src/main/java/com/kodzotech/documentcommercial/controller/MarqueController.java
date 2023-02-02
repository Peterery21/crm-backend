package com.kodzotech.documentcommercial.controller;

import com.kodzotech.documentcommercial.dto.MarqueDto;
import com.kodzotech.documentcommercial.service.MarqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marques")
@RequiredArgsConstructor
public class MarqueController {

    private final MarqueService marqueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody MarqueDto marqueDto) {
        marqueService.save(marqueDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @RequestBody MarqueDto marqueDto) {
        marqueDto.setId(id);
        marqueService.save(marqueDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MarqueDto getMarque(@PathVariable Long id) {
        return marqueService.getMarque(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<MarqueDto> getAllMarque() {
        return marqueService.getAllMarques();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        marqueService.delete(id);
    }

}
