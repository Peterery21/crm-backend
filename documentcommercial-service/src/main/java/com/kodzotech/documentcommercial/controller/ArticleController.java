package com.kodzotech.documentcommercial.controller;


import com.kodzotech.documentcommercial.dto.ArticleDto;
import com.kodzotech.documentcommercial.dto.ArticleResponse;
import com.kodzotech.documentcommercial.service.ArticleService;
import com.kodzotech.documentcommercial.utils.TresosoftConstant;
import com.kodzotech.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@ModelAttribute ArticleDto articleDto,
                     HttpServletRequest request) {

        JwtProvider jwtProvider = new JwtProvider();
        articleDto.setSocieteId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID));
        articleDto.setEntiteId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.ENTITE_ID));

        articleService.save(articleDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @ModelAttribute ArticleDto articleDto) {
        articleDto.setId(id);
        articleService.save(articleDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArticleDto getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleDto> getAllArticle() {
        return articleService.getAllArticle();
    }

    @GetMapping("/achat")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleDto> getAllAchatArticle(HttpServletRequest request) {
        JwtProvider jwtProvider = new JwtProvider();
        Long societeId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID);
        return articleService.getAllAchatArticle(societeId);
    }

    @GetMapping("/vente")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleDto> getAllVenteArticle(HttpServletRequest request) {
        JwtProvider jwtProvider = new JwtProvider();
        Long societeId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID);
        return articleService.getAllVenteArticle(societeId);
    }

    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public Long getNbreArticle() {
        return articleService.getNbreArticle();
    }

    @GetMapping("/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleResponse> getAllArticle(@PathVariable Integer page,
                                               @PathVariable Integer size) {
        return articleService.getAllArticle(page, size);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        articleService.delete(id);
    }

}
