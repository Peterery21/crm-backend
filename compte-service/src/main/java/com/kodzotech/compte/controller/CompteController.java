package com.kodzotech.compte.controller;

import com.kodzotech.compte.dto.CompteDto;
import com.kodzotech.compte.dto.CompteResponse;
import com.kodzotech.compte.dto.StatCompteDto;
import com.kodzotech.compte.service.CompteService;
import com.kodzotech.compte.utils.TresosoftConstant;
import com.kodzotech.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comptes")
@RequiredArgsConstructor
public class CompteController {

    private final CompteService compteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CompteDto compteDto,
                       HttpServletRequest request) {

        JwtProvider jwtProvider = new JwtProvider();
        compteDto.setSocieteId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID));
        compteDto.setEntiteId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.ENTITE_ID));
        compteDto.setUtilisateurId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.UTILISATEUR_ID));

        compteService.save(compteDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @RequestBody CompteDto compteDto) {
        compteDto.setId(id);
        compteService.save(compteDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompteDto getCompte(@PathVariable Long id) {
        return compteService.getCompte(id);
    }

    @GetMapping("/info/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompteResponse getFullCompte(@PathVariable Long id) {
        return compteService.getFullCompte(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CompteResponse> getAllCompte() {
        return compteService.getAllCompte();
    }

    @GetMapping("/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<CompteResponse> getAllCompte(@PathVariable Integer page,
                                             @PathVariable Integer size) {
        return compteService.getAllCompte(page, size);
    }

    @GetMapping("/client")
    @ResponseStatus(HttpStatus.OK)
    public List<CompteResponse> getAllClient() {
        return compteService.getAllClient();
    }

    @GetMapping("/prospect")
    @ResponseStatus(HttpStatus.OK)
    public List<CompteResponse> getAllProspect() {
        return compteService.getAllProspect();
    }

    @GetMapping("/fournisseur")
    @ResponseStatus(HttpStatus.OK)
    public List<CompteResponse> getAllFournisseur() {
        return compteService.getAllFournisseur();
    }

    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public List<CompteDto> getComptesById(@RequestParam List<Long> ids) {
        return compteService.getComptesById(ids);
    }

    @GetMapping("/stat")
    @ResponseStatus(HttpStatus.OK)
    public StatCompteDto getStatCompte() {
        return compteService.getStatCompte();
    }

    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public Long getNbreComptes() {
        return compteService.getNbreComptes();
    }

    @GetMapping("/secteuractivite/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkUsedSecteur(@PathVariable Long id) {
        return compteService.checkUsedSecteur(id);
    }

    @GetMapping("/comptes/adresse/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkUsedAdresse(@PathVariable Long id) {
        return compteService.checkUsedAdresse(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        compteService.delete(id);
    }
}
