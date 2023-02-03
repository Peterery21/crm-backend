package com.kodzotech.entite.service.impl;

import com.kodzotech.entite.dto.EntiteChart;
import com.kodzotech.entite.dto.EntiteResponse;
import com.kodzotech.entite.model.Entite;
import com.kodzotech.entite.service.EntiteChartService;
import com.kodzotech.entite.exception.EntiteException;
import com.kodzotech.entite.repository.EntiteRepository;
import com.kodzotech.entite.service.EntiteMapperService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@RefreshScope
@RequiredArgsConstructor
public class EntiteChartServiceImpl implements EntiteChartService {

    private final EntiteRepository entiteRepository;
    private final EntiteMapperService entiteMapperService;

    @Value("${chart.image}")
    private String image;

    @Override
    @Transactional(readOnly = true)
    public List<EntiteResponse> getAllEntitesBySociete(Long societeId) {
        List<EntiteResponse> entiteResponses = entiteMapperService.entityToResponse(entiteRepository.findAllBySocieteId(societeId));
        List<EntiteResponse> finalList = new ArrayList<>();
        Integer minNiveau = entiteResponses.stream().mapToInt(value -> value.getNiveau()).min().getAsInt();
        entiteResponses.stream()
                .filter(entiteResponse -> entiteResponse.getNiveau() == minNiveau)
                .forEach(entiteResponse -> {
                    finalList.add(entiteResponse);
                    finalList.addAll(getEntiteChild(entiteResponse, entiteResponses));
                });
        return finalList;
    }

    @Override
    public List<EntiteResponse> getEntiteChild(EntiteResponse entiteResponse, List<EntiteResponse> entiteResponses) {
        List<EntiteResponse> finalList = new ArrayList<>();
        List<EntiteResponse> childs = getParentChild(entiteResponse.getId(), entiteResponses);
        if (childs != null && childs.size() > 0) {
            childs.stream()
                    .forEach(e -> {
                        finalList.add(e);
                        finalList.addAll(getEntiteChild(e, entiteResponses));
                    });
        }
        return finalList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntiteChart> getEntiteChart(Long societeId) {
        List<EntiteResponse> entiteResponses = entiteMapperService.entityToResponse(entiteRepository.findAllBySocieteId(societeId));
        Integer minNiveau = entiteResponses.stream().mapToInt(value -> value.getNiveau()).min().getAsInt();
        List<EntiteChart> entiteCharts = new ArrayList<>();
        entiteResponses.stream()
                .filter(entiteResponse -> entiteResponse.getNiveau() == minNiveau)
                .forEach(entiteResponse -> {
                    EntiteChart entiteChart = EntiteChart
                            .builder()
                            .id(entiteResponse.getId())
                            .name(entiteResponse.getNom())
                            .image(getImage())
                            .build();
                    entiteCharts.add(getChartChild(entiteChart, entiteResponses));
                });
        return entiteCharts;
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean hasChild(Long entiteId) {
        return entiteRepository.existsByParentId(entiteId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> getChildsId(Long entiteId) {
        Entite entite = entiteRepository.findById(entiteId)
                .orElseThrow(() -> new EntiteException("erreur.entite.id.non.trouve"));
        List<EntiteResponse> entiteResponses = entiteMapperService.entityToResponse(entiteRepository.findAllBySocieteId(entite.getSocieteId()));
        EntiteResponse entiteResponse = entiteResponses.stream()
                .filter(e -> e.getId() == entiteId).findFirst().get();
        List<Long> finalIds = new ArrayList<>();
        finalIds.add(entiteId);
        finalIds.addAll(getEntiteChild(entiteResponse, entiteResponses)
                .stream().map(e -> e.getId())
                .collect(Collectors.toList()));
        return finalIds;
    }

    @Override
    public EntiteChart getChartChild(EntiteChart entiteChart, List<EntiteResponse> entiteResponses) {
        List<EntiteChart> childs = getParentChild(entiteChart.getId(), entiteResponses)
                .stream()
                .map(entiteResponse -> EntiteChart
                        .builder()
                        .id(entiteResponse.getId())
                        .name(entiteResponse.getNom())
                        .image(getImage())
                        .build())
                .collect(Collectors.toList());
        if (childs != null && childs.size() > 0) {
            entiteChart.setChilds(childs);
            entiteChart.getChilds()
                    .stream()
                    .forEach(e -> e = getChartChild(e, entiteResponses));
        }
        return entiteChart;
    }

    @Override
    public List<EntiteResponse> getParentChild(Long parentId, List<EntiteResponse> entiteResponses) {
        return entiteResponses.stream().filter(entiteResponse -> entiteResponse.getParent() != null
                        && entiteResponse.getParent().getId() == parentId)
                .collect(Collectors.toList());
    }
}
