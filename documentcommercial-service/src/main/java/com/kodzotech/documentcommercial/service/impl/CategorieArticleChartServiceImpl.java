package com.kodzotech.documentcommercial.service.impl;

import com.kodzotech.documentcommercial.dto.CategorieArticleResponse;
import com.kodzotech.documentcommercial.exception.CategorieArticleException;
import com.kodzotech.documentcommercial.repository.CategorieArticleRepository;
import com.kodzotech.documentcommercial.mapper.CategorieArticleMapper;
import com.kodzotech.documentcommercial.model.CategorieArticle;
import com.kodzotech.documentcommercial.service.CategorieArticleChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieArticleChartServiceImpl implements CategorieArticleChartService {

    private final CategorieArticleRepository categorieArticleRepository;
    private final CategorieArticleMapper categorieArticleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategorieArticleResponse> getAllCategorieArticlesBySociete() {
        List<CategorieArticleResponse> categorieArticleResponses = categorieArticleMapper.entitiesToResponse(categorieArticleRepository.findAll());
        List<CategorieArticleResponse> finalList = new ArrayList<>();
        Integer minNiveau = categorieArticleResponses.stream().mapToInt(value -> value.getNiveau()).min().getAsInt();
        categorieArticleResponses.stream()
                .filter(categorieArticleResponse -> categorieArticleResponse.getNiveau() == minNiveau)
                .forEach(categorieArticleResponse -> {
                    finalList.add(categorieArticleResponse);
                    finalList.addAll(getCategorieArticleChild(categorieArticleResponse, categorieArticleResponses));
                });
        return finalList;
    }

    @Override
    public List<CategorieArticleResponse> getCategorieArticleChild(CategorieArticleResponse categorieArticleResponse, List<CategorieArticleResponse> categorieArticleResponses) {
        List<CategorieArticleResponse> finalList = new ArrayList<>();
        List<CategorieArticleResponse> childs = getParentChild(categorieArticleResponse.getId(), categorieArticleResponses);
        if (childs != null && childs.size() > 0) {
            childs.stream()
                    .forEach(e -> {
                        finalList.add(e);
                        finalList.addAll(getCategorieArticleChild(e, categorieArticleResponses));
                    });
        }
        return finalList;
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean hasChild(Long categorieArticleId) {
        return categorieArticleRepository.existsByParentId(categorieArticleId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> getChildsId(Long categorieArticleId) {
        CategorieArticle categorieArticle = categorieArticleRepository.findById(categorieArticleId)
                .orElseThrow(() -> new CategorieArticleException("erreur.categorieArticle.id.non.trouve"));
        List<CategorieArticleResponse> categorieArticleResponses = categorieArticleMapper.entitiesToResponse(categorieArticleRepository.findAllBySocieteId(categorieArticle.getSocieteId()));
        CategorieArticleResponse categorieArticleResponse = categorieArticleResponses.stream()
                .filter(e -> e.getId() == categorieArticleId).findFirst().get();
        List<Long> finalIds = new ArrayList<>();
        finalIds.add(categorieArticleId);
        finalIds.addAll(getCategorieArticleChild(categorieArticleResponse, categorieArticleResponses)
                .stream().map(e -> e.getId())
                .collect(Collectors.toList()));
        return finalIds;
    }

    @Override
    public List<CategorieArticleResponse> getParentChild(Long parentId, List<CategorieArticleResponse> categorieArticleResponses) {
        return categorieArticleResponses.stream().filter(categorieArticleResponse -> categorieArticleResponse.getParent() != null
                        && categorieArticleResponse.getParent().getId() == parentId)
                .collect(Collectors.toList());
    }
}
