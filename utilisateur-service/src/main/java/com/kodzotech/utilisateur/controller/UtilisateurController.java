package com.kodzotech.utilisateur.controller;

import com.kodzotech.utilisateur.service.impl.UtilisateurServiceImpl;
import com.kodzotech.security.jwt.JwtProvider;
import com.kodzotech.utilisateur.dto.UtilisateurDto;
import com.kodzotech.utilisateur.dto.UtilisateurResponse;
import com.kodzotech.utilisateur.utils.TresosoftConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurServiceImpl utilisateurService;

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UtilisateurResponse getUtilisateurByUsername(@PathVariable String username) {
        return utilisateurService.getUtilisateurByUsername(username);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UtilisateurDto getUtilisateur(@PathVariable Long id) {
        return utilisateurService.getUtilisateur(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUtilisateur(@Valid @RequestBody UtilisateurDto utilisateurDto,
                                  HttpServletRequest request) {
        JwtProvider jwtProvider = new JwtProvider();
        Long societeId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID);
        Long entiteId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.ENTITE_ID);
        utilisateurDto.setEntiteId(entiteId);
        utilisateurDto.setSocieteId(societeId);
        utilisateurService.create(utilisateurDto);
    }

//    @PutMapping("/{username}")
//    @ResponseStatus(code = HttpStatus.OK)
//    public void updateUtilisateur(@PathVariable String username, @Valid @RequestBody UtilisateurDto utilisateurDto) {
//        utilisateurService.update(username, utilisateurDto);
//    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateUtilisateur(@PathVariable Long id, @Valid @RequestBody UtilisateurDto utilisateurDto) {
        utilisateurDto.setId(id);
        utilisateurService.update(utilisateurDto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurDto> getAllUtilisateur(HttpServletRequest request) {
        JwtProvider jwtProvider = new JwtProvider();
        Long societeId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID);
        return utilisateurService.getAllUtilisateur(societeId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurResponse> getAllUtilisateurInfo(HttpServletRequest request) {
        JwtProvider jwtProvider = new JwtProvider();
        Long societeId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID);
        return utilisateurService.getAllUtilisateurInfo(societeId);
    }

    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurDto> getUtilisateursById(@RequestParam List<Long> ids) {
        return utilisateurService.getUtilisateursById(ids);
    }

}
