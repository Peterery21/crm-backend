package com.kodzotech.compte.controller;

import com.kodzotech.compte.dto.PaysDto;
import com.kodzotech.compte.service.PaysService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pays")
@RequiredArgsConstructor
public class PaysController {

    private final PaysService paysService;

    @GetMapping("/{lang}")
    @ResponseStatus(HttpStatus.OK)
    public List<PaysDto> getAllPays(@PathVariable String lang) {
        if ("fr".equals(lang)) {
            return paysService.getPaysFrList();
        } else {
            return paysService.getPaysEnList();
        }
    }

    @GetMapping("/code/{code}")
    @ResponseStatus(HttpStatus.OK)
    public PaysDto getPays(@PathVariable String code) {
        return paysService.getPays(code);
    }

}
