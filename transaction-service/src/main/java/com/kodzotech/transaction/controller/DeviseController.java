package com.kodzotech.transaction.controller;

import com.kodzotech.transaction.dto.DeviseDto;
import com.kodzotech.transaction.service.DeviseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devises")
@RequiredArgsConstructor
public class DeviseController {

    private final DeviseService deviseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createDevise(@RequestBody DeviseDto deviseDto) {
        deviseService.save(deviseDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateDevise(@PathVariable Long id, @RequestBody DeviseDto deviseDto) {
        deviseDto.setId(id);
        deviseService.save(deviseDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviseDto getDevise(@PathVariable Long id) {
        return deviseService.getDevise(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<DeviseDto> getAllDevise() {
        return deviseService.getAllDevise();
    }

    @GetMapping("/defaut")
    @ResponseStatus(HttpStatus.OK)
    public List<DeviseDto> getDefaultDevises() {
        return deviseService.getDefaultList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        deviseService.delete(id);
    }

    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public List<DeviseDto> getDevisesById(@RequestParam List<Long> ids) {
        return deviseService.getDevisesById(ids);
    }
}
